package fr.heriamc.bukkit.packet;

import fr.heriamc.api.messaging.packet.HeriaPacket;
import fr.heriamc.api.messaging.packet.HeriaPacketChannel;

public class BukkitBroadcastMessagePacket extends HeriaPacket {

    private final String message;
    private final int neededPower;

    public BukkitBroadcastMessagePacket(String message) {
        this(message, 0);
    }

    public BukkitBroadcastMessagePacket(String message, int neededPower) {
        super(HeriaPacketChannel.API);
        this.message = message;
        this.neededPower = neededPower;
    }

    public String getMessage() {
        return message;
    }

    public int getNeededPower() {
        return neededPower;
    }
}
