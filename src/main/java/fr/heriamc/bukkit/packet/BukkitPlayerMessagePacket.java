package fr.heriamc.bukkit.packet;

import fr.heriamc.api.messaging.packet.HeriaPacket;
import fr.heriamc.api.messaging.packet.HeriaPacketChannel;

import java.util.UUID;

public class BukkitPlayerMessagePacket extends HeriaPacket {

    private final UUID player;
    private final String message;

    public BukkitPlayerMessagePacket(UUID player, String message) {
        super(HeriaPacketChannel.API);
        this.player = player;
        this.message = message;
    }

    public UUID getPlayer() {
        return player;
    }

    public String getMessage() {
        return message;
    }
}
