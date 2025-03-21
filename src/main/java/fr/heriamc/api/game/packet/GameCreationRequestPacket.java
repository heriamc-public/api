package fr.heriamc.api.game.packet;

import fr.heriamc.api.messaging.packet.HeriaPacket;
import fr.heriamc.api.messaging.packet.HeriaPacketChannel;
import fr.heriamc.api.server.HeriaServerType;
import fr.heriamc.api.game.size.GameSize;

import java.util.UUID;

public class GameCreationRequestPacket extends HeriaPacket {

    private final UUID requestID;
    private final String server;
    private final HeriaServerType serverType;
    private final GameSize size;

    public GameCreationRequestPacket(UUID requestID, String server, HeriaServerType serverType, GameSize size) {
        super(HeriaPacketChannel.GAME);
        this.requestID = requestID;
        this.server = server;
        this.serverType = serverType;
        this.size = size;
    }

    public UUID getRequestID() {
        return requestID;
    }

    public String getServer() {
        return server;
    }

    public HeriaServerType getServerType() {
        return serverType;
    }

    public GameSize getSize() {
        return size;
    }
}
