package fr.heriamc.api.game.packet;

import fr.heriamc.api.messaging.packet.HeriaPacket;
import fr.heriamc.api.messaging.packet.HeriaPacketChannel;

import java.util.UUID;

public class GameCreatedPacket extends HeriaPacket {

    private final UUID requestID;
    private final String gameName;
    private final String serverName;
    private final GameCreationResult result;

    public GameCreatedPacket(UUID requestID, String gameName, String serverName, GameCreationResult result) {
        super(HeriaPacketChannel.GAME);
        this.requestID = requestID;
        this.gameName = gameName;
        this.serverName = serverName;
        this.result = result;
    }

    public UUID getRequestID() {
        return requestID;
    }

    public String getGameName() {
        return gameName;
    }

    public String getServerName() {
        return serverName;
    }

    public GameCreationResult getResult() {
        return result;
    }

}
