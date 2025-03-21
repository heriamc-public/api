package fr.heriamc.api.queue;

import fr.heriamc.api.HeriaAPI;
import fr.heriamc.api.data.SerializableData;
import fr.heriamc.api.game.size.GameSize;
import fr.heriamc.api.server.HeriaServerType;
import fr.heriamc.api.user.HeriaPlayer;
import fr.heriamc.bukkit.HeriaBukkit;

import java.util.Set;
import java.util.UUID;

public class HeriaQueue implements SerializableData<UUID> {

    private UUID id;

    private QueueType queueType;
    private QueueServerType queueServerType;

    private HeriaServerType serverType;
    private GameSize gameSize;

    private String server;
    private String game;

    private Set<UUID> players;

    public HeriaQueue(UUID id, QueueType queueType, QueueServerType queueServerType, HeriaServerType serverType, GameSize gameSize, String server, String game, Set<UUID> players) {
        this.id = id;
        this.queueType = queueType;
        this.queueServerType = queueServerType;
        this.serverType = serverType;
        this.gameSize = gameSize;
        this.server = server;
        this.game = game;
        this.players = players;
    }

    public UUID getId() {
        return id;
    }

    public HeriaQueue setId(UUID id) {
        this.id = id;
        return this;
    }

    public QueueType getQueueType() {
        return queueType;
    }

    public HeriaQueue setQueueType(QueueType queueType) {
        this.queueType = queueType;
        return this;
    }

    public QueueServerType getQueueServerType() {
        return queueServerType;
    }

    public HeriaQueue setQueueServerType(QueueServerType queueServerType) {
        this.queueServerType = queueServerType;
        return this;
    }

    public HeriaServerType getServerType() {
        return serverType;
    }

    public HeriaQueue setServerType(HeriaServerType serverType) {
        this.serverType = serverType;
        return this;
    }

    public GameSize getGameSize() {
        return gameSize;
    }

    public HeriaQueue setGameSize(GameSize gameSize) {
        this.gameSize = gameSize;
        return this;
    }

    public String getServer() {
        return server;
    }

    public HeriaQueue setServer(String server) {
        this.server = server;
        return this;
    }

    public String getGame() {
        return game;
    }

    public HeriaQueue setGame(String game) {
        this.game = game;
        return this;
    }

    public Set<UUID> getPlayers() {
        return players;
    }

    public HeriaQueue setPlayers(Set<UUID> players) {
        this.players = players;
        return this;
    }

    @Override
    public UUID getIdentifier() {
        return id;
    }

    @Override
    public void setIdentifier(UUID identifier) {
        this.id = identifier;
    }

    public int getPlayerPosition(UUID player){
        int i = 1;
        for (UUID uuid : this.players) {
            if(uuid.equals(player)){
                return i;
            }
            i++;
        }

        return -1;
    }
    public enum QueueType {

        KNOWN,
        UNKNOWN

    }
    public enum QueueServerType {

        GAME,
        SERVER

        ;

    }
}
