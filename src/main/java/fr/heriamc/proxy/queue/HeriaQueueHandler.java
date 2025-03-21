package fr.heriamc.proxy.queue;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.scheduler.ScheduledTask;
import fr.heriamc.api.game.GameState;
import fr.heriamc.api.game.HeriaGameInfo;
import fr.heriamc.api.game.HeriaGamesList;
import fr.heriamc.api.messaging.packet.HeriaPacket;
import fr.heriamc.api.queue.HeriaQueue;
import fr.heriamc.api.queue.HeriaQueue.QueueServerType;
import fr.heriamc.api.queue.HeriaQueue.QueueType;
import fr.heriamc.api.server.HeriaServer;
import fr.heriamc.api.server.HeriaServerType;
import fr.heriamc.api.user.HeriaPlayer;
import fr.heriamc.api.game.size.GameSize;
import fr.heriamc.api.utils.TimeUtils;
import fr.heriamc.bukkit.utils.Title;
import fr.heriamc.proxy.HeriaProxy;
import fr.heriamc.proxy.pool.HeriaPool;
import fr.heriamc.proxy.utils.AutoUpdatingList;
import fr.heriamc.proxy.utils.ProxyPacketUtil;
import net.kyori.adventure.text.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class HeriaQueueHandler {

    private final HeriaProxy proxy = HeriaProxy.get();
    private final AutoUpdatingList<UUID> playerQueue = new AutoUpdatingList<>(1000, Comparator.comparing(this::calculatePriority));
    private long lastProcess;

    private final HeriaQueue queue;
    private HeriaPool serversPool;
    private ScheduledTask schedule;

    public static final int MAX_SERVER_SIZE = 50;

    public HeriaQueueHandler(String server){
        this(new HeriaQueue(UUID.randomUUID(), QueueType.KNOWN, QueueServerType.SERVER, null, null, server, null, new HashSet<>()));
    }

    public HeriaQueueHandler(String server, String gameName){
        this(new HeriaQueue(UUID.randomUUID(), QueueType.KNOWN, QueueServerType.GAME, null, null, server, gameName, new HashSet<>()));
    }

    public HeriaQueueHandler(HeriaServerType serverType){
        this(new HeriaQueue(UUID.randomUUID(), QueueType.UNKNOWN, QueueServerType.SERVER, serverType, null, null, null, new HashSet<>()));
        this.serversPool = proxy.getPoolManager().getServerPool(serverType);
    }

    public HeriaQueueHandler(HeriaServerType serverType, GameSize gameSize){
            this(new HeriaQueue(UUID.randomUUID(), QueueType.UNKNOWN, QueueServerType.GAME, serverType, gameSize, null, null, new HashSet<>()));
            this.serversPool = proxy.getPoolManager().getGamePool(serverType, gameSize);
    }

    private HeriaQueueHandler(HeriaQueue queue) {
        System.out.println("Private constructor invoked for HeriaQueueHandler with queue: " + queue);
        this.queue = queue;

        this.schedule = proxy.getServer().getScheduler().buildTask(proxy, (scheduledTask -> {
            updateQueue();
            processPlayers();
        })).repeat(250, TimeUnit.MILLISECONDS).schedule();
    }

    public void disable() {
        System.out.println("Disabling HeriaQueueHandler for queue: " + queue);
        this.schedule.cancel();

        for (UUID player : this.queue.getPlayers()) {
            HeriaPlayer heriaPlayer = this.proxy.getApi().getPlayerManager().get(player);

            if(heriaPlayer == null){
                continue;
            }

            heriaPlayer.setQueue(null);
            this.proxy.getApi().getPlayerManager().save(heriaPlayer);
        }

        this.proxy.getQueueManager().removeQueue(this);
        this.proxy.getApi().getQueueManager().remove(this.queue);
    }

    public void processPlayers(){
        System.out.println("Processing players in queue...");

        /*if (this.playerQueue.size() == 0 && System.currentTimeMillis() - this.lastProcess >= TimeUnit.MINUTES.toMillis(1)) {
            System.out.println("Queue empty for more than a minute, disabling handler.");
            this.disable();
        }*/

        for (UUID player : this.playerQueue.getList()) {
            System.out.println("Processing player: " + player);
            processPlayer(player);
        }

        if (this.playerQueue.size() > 0) {
            System.out.println("Updating last process time.");
            this.lastProcess = System.currentTimeMillis();
        }
    }

    public void processPlayer(UUID player) {
        System.out.println("Processing individual player: " + player);

        if (this.serversPool == null) {
            System.out.println("No servers pool available. QueueServerType: " + queue.getQueueServerType());

            if (queue.getQueueServerType() == QueueServerType.GAME) {
                HeriaGamesList heriaGamesList = proxy.getApi().getHeriaGameManager().get(queue.getServer());
                System.out.println("Retrieved HeriaGamesList: " + heriaGamesList);

                HeriaGameInfo gameInfo = null;
                for (HeriaGameInfo game : heriaGamesList.getGames()) {
                    if (game.getGameName().equals(queue.getGame())) {
                        gameInfo = game;
                        break;
                    }
                }

                if (gameInfo == null) {
                    System.out.println("No matching game info found, exiting processPlayer.");
                    return;
                }

                boolean spectator = !gameInfo.getState().is(GameState.WAIT, GameState.ALWAYS_PLAYING, GameState.STARTING)
                        || gameInfo.getAlivePlayersCount() >= gameInfo.getGameSize().getMaxPlayer();
                System.out.println("Spectator mode: " + spectator);

                List<HeriaPacket> packets = ProxyPacketUtil.buildJoinGame(player, queue.getServer(), queue.getGame(), spectator);
                for (HeriaPacket packet : packets) {
                    System.out.println("Sending packet: " + packet);
                    proxy.getApi().getMessaging().send(packet);
                }

                this.removePlayer(player);
            } else if (queue.getQueueServerType() == QueueServerType.SERVER) {
                HeriaServer server = proxy.getApi().getServerManager().get(queue.getServer());
                System.out.println("Retrieved HeriaServer: " + server);

                if (server.getConnected().size() >= MAX_SERVER_SIZE) {
                    System.out.println("Server full, notifying player and removing from queue.");
                    Player connected = proxy.getServer().getPlayer(player).orElse(null);

                    if (connected != null) {
                        connected.sendMessage(Component.text("Vous avez été exclu de la file d'attente vers " + queue.getServer() + ": Serveur complet."));
                    }

                    this.removePlayer(player);
                    return;
                }

                List<HeriaPacket> packets = ProxyPacketUtil.buildJoinServer(player, queue.getServer());
                for (HeriaPacket packet : packets) {
                    System.out.println("Sending packet: " + packet);
                    proxy.getApi().getMessaging().send(packet);
                }

                this.removePlayer(player);
            }
            return;
        }

        Player proxyPlayer = proxy.getServer().getPlayer(player).orElse(null);
        if (proxyPlayer != null) {
            System.out.println("Sending action bar to player in queue: " + proxyPlayer);
            String serverName = queue.getServer() != null ? queue.getServer() : queue.getServerType().getName();
            String gameSize = queue.getGameSize() != null ? queue.getGameSize().getName() : "";
            HeriaPlayer heriaPlayer = proxy.getApi().getPlayerManager().get(player);

            proxyPlayer.sendActionBar(Component.text("§7File d'attente vers: §6" + serverName + " " + gameSize +
                    " §8▏ §7Position: §6" + queue.getPlayerPosition(proxyPlayer.getUniqueId()) + "§8/§6" + queue.getPlayers().size()
                    + " §8▏ §7Durée: §6" + TimeUtils.formatDurationMillisToString(System.currentTimeMillis() - heriaPlayer.getJoinQueueTime())));
        }

        if (this.serversPool.isAvailable()) {
            System.out.println("Servers pool is available. Sending player to server.");
            List<HeriaPacket> packets = this.serversPool.createPackets(player);
            for (HeriaPacket packet : packets) {
                System.out.println("Sending packet: " + packet);
                proxy.getApi().getMessaging().send(packet);
            }
            this.removePlayer(player);
        }
    }

    private int calculatePriority(UUID player) {
        HeriaPlayer account = proxy.getApi().getPlayerManager().get(player);
        int priority = account.getRank().getTabPriority();
        System.out.println("Calculated priority for player " + player + ": " + priority);
        return priority;
    }

    public void addPlayer(UUID player) {
        System.out.println("Adding player to queue: " + player);
        HeriaPlayer heriaPlayer = proxy.getApi().getPlayerManager().get(player);
        heriaPlayer.setQueue(this.queue.getId());
        heriaPlayer.setJoinQueueTime(System.currentTimeMillis());
        proxy.getApi().getPlayerManager().save(heriaPlayer);

        this.playerQueue.add(player);
        updateQueue();
    }

    public void removePlayer(UUID player) {
        System.out.println("Removing player from queue: " + player);
        HeriaPlayer heriaPlayer = proxy.getApi().getPlayerManager().get(player);
        heriaPlayer.setQueue(null);
        proxy.getApi().getPlayerManager().save(heriaPlayer);

        this.playerQueue.remove(player);
        updateQueue();
    }

    public void updateQueue() {
        System.out.println("Updating queue with current player list.");
        this.queue.getPlayers().clear();
        this.queue.getPlayers().addAll(this.playerQueue.getList());
        this.proxy.getApi().getQueueManager().save(this.queue);
    }

    public HeriaQueue getQueue() {
        return queue;
    }
}