package fr.heriamc.proxy.queue.packet;

import fr.heriamc.api.game.size.GameSize;
import fr.heriamc.api.messaging.packet.HeriaPacket;
import fr.heriamc.api.messaging.packet.HeriaPacketChannel;
import fr.heriamc.api.server.HeriaServerType;

import java.util.UUID;

public class QueueJoinPacket extends HeriaPacket {

    private final UUID player;
    private final String serverName;
    private final String gameName;
    private final HeriaServerType serverType;
    private final GameSize gameSize;

    public QueueJoinPacket(UUID player, String serverName, String gameName, HeriaServerType serverType, GameSize gameSize) {
        super(HeriaPacketChannel.QUEUE);
        this.player = player;
        this.serverName = serverName;
        this.gameName = gameName;
        this.serverType = serverType;
        this.gameSize = gameSize;
    }

    public UUID getPlayer() {
        return player;
    }

    public String getServerName() {
        return serverName;
    }

    public String getGameName() {
        return gameName;
    }

    public HeriaServerType getServerType() {
        return serverType;
    }

    public GameSize getGameSize() {
        return gameSize;
    }
}
