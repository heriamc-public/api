package fr.heriamc.proxy.packet.model;

import fr.heriamc.api.messaging.packet.HeriaPacket;
import fr.heriamc.api.messaging.packet.HeriaPacketChannel;

import java.util.UUID;

public class SendPlayerPacket extends HeriaPacket {

    private final UUID playerUUID;
    private final String serverTarget;

    public SendPlayerPacket(UUID playerUUID, String serverTarget) {
        super(HeriaPacketChannel.API);
        this.playerUUID = playerUUID;
        this.serverTarget = serverTarget;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public String getServerTarget() {
        return serverTarget;
    }
}
