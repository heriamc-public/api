package fr.heriamc.proxy.pool.types;

import fr.heriamc.api.game.GameState;
import fr.heriamc.api.game.HeriaGameInfo;
import fr.heriamc.api.messaging.packet.HeriaPacket;
import fr.heriamc.api.messaging.packet.HeriaPacketReceiver;
import fr.heriamc.api.server.HeriaServerType;
import fr.heriamc.api.game.HeriaGamesList;
import fr.heriamc.api.game.packet.GameCreatedPacket;
import fr.heriamc.api.game.packet.GameCreationRequestPacket;
import fr.heriamc.api.game.packet.GameCreationResult;
import fr.heriamc.api.game.size.GameSize;
import fr.heriamc.proxy.HeriaProxy;
import fr.heriamc.proxy.utils.ProxyPacketUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class GameServerPool extends ServerPool implements HeriaPacketReceiver {

    private final GameSize gameSize;

    private boolean creatingGames = true;
    private boolean waitingServer = true;

    private final List<String> lastCreated = new ArrayList<>();
    private UUID lastRequestID;

    public GameServerPool(HeriaProxy proxy, HeriaServerType serverType, GameSize gameSize) {
        super(proxy, serverType);
        this.gameSize = gameSize;
        System.out.println("GameServerPool created with GameSize: " + gameSize);

        this.proxy.getServer().getScheduler().buildTask(proxy, (scheduledTask -> {
            if(waitingServer && isServerEnabled){
                waitingServer = false;
            }

            if(!this.isAvailable()){
                createGame();
            }
        })).repeat(500, TimeUnit.MILLISECONDS).schedule();
    }

    public void createGame(){
        System.out.println("Attempting to create game...");

        /*if(lastServer == null){
            System.out.println("No server found, creating a new server.");
            createServer();
            this.waitingServer = true;
        }*/

        if(waitingServer){
            System.out.println("Waiting for server to become available...");
            return;
        }

        if(!creatingGames){
            System.out.println("Game creation paused.");
            return;
        }

        this.lastRequestID = UUID.randomUUID();
        System.out.println("Sending game creation request with ID: " + lastRequestID);
        this.proxy.getApi().getMessaging().send(new GameCreationRequestPacket(this.lastRequestID, lastServer, serverType, gameSize));
        this.creatingGames = false;
    }

    @Override
    public boolean isAvailable() {
        System.out.println("Checking availability, lastCreated is empty: " + this.lastCreated.isEmpty());
        return !this.lastCreated.isEmpty();
    }

    @Override
    public List<HeriaPacket> createPackets(UUID player) {
        System.out.println("Creating packets for player: " + player);
        List<HeriaPacket> packets = ProxyPacketUtil.buildJoinGame(player, lastServer, lastCreated.get(0), false);

        if(!isOldCorrect()){
            System.out.println("Old game is no longer valid, removing and creating a new game.");
            lastCreated.remove(0);
        }

        return packets;
    }

    private boolean isOldCorrect(){
        System.out.println("Checking if the old game is correct...");
        System.out.println("Old server name is: " + this.lastServer);
        HeriaGamesList heriaGamesList = proxy.getApi().getHeriaGameManager().get(this.lastServer);

        HeriaGameInfo game = null;
        for (HeriaGameInfo gameInfo : heriaGamesList.getGames()) {
            System.out.println("Found game info: " + gameInfo);
            if(gameInfo.getGameName().equals(this.lastCreated.get(0))){
                game = gameInfo;
                System.out.println("Found matching game: " + gameInfo.getGameName());
            }
        }

        if(game == null){
            System.out.println("Game is null, returning false.");
            return false;
        }

        if(game.getAlivePlayersCount() >= this.gameSize.getMaxPlayer()){
            System.out.println("Game is full, returning false.");
            return false;
        }

        System.out.println("Game state is " + game.getState());
        //System.out.println("Json game is " + GsonUtils.get().toJson(game));
        boolean stateValid = game.getState().is(GameState.WAIT, GameState.ALWAYS_PLAYING, GameState.STARTING);
        System.out.println("Game state is valid: " + stateValid);
        return stateValid;
    }

    @Override
    public void onPacket(String channel, HeriaPacket packet) {
        System.out.println("Executing packet received on channel: " + channel);

        if(!(packet instanceof GameCreatedPacket gamePacket)){
            System.out.println("Packet is not an instance of GameCreatedPacket, ignoring...");
            return;
        }

        if(!gamePacket.getRequestID().equals(lastRequestID)){
            System.out.println("Packet request ID does not match last request ID, ignoring...");
            return;
        }

        GameCreationResult result = gamePacket.getResult();
        System.out.println("Game creation result: " + result);

        if(result == GameCreationResult.FAIL){
            System.out.println("Game creation failed, creating a new server.");
            this.createServer();
            this.waitingServer = true;
            this.lastCreated.clear();
            return;
        }

        this.creatingGames = true;
        this.lastCreated.add(gamePacket.getGameName());
        System.out.println("Game created successfully with name: " + gamePacket.getGameName());
    }

    @Override
    public void onInstanceStop(String instanceName) {
        System.out.println("Old instance stopped, creating a new server.");
        if(instanceName.equals(this.lastServer)){
            this.createServer();
            this.waitingServer = true;

            this.lastCreated.clear();
        }
    }

    @Override
    public HeriaServerType getServerType() {
        System.out.println("Getting server type: " + super.getServerType());
        return super.getServerType();
    }

    @Override
    public GameSize getGameSize() {
        System.out.println("Getting game size: " + gameSize);
        return gameSize;
    }
}
