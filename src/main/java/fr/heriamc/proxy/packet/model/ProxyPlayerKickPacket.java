package fr.heriamc.proxy.packet.model;

import fr.heriamc.api.messaging.packet.HeriaPacket;
import fr.heriamc.api.messaging.packet.HeriaPacketChannel;

import java.util.UUID;

public class ProxyPlayerKickPacket extends HeriaPacket {

    private final UUID player;
    private final String reason;

    public ProxyPlayerKickPacket(UUID player, String reason) {
        super(HeriaPacketChannel.API);
        this.player = player;
        this.reason = reason;
    }

    public UUID getPlayer() {
        return player;
    }

    public String getReason() {
        return reason;
    }
}
