package fr.heriamc.proxy.queue.packet;

import fr.heriamc.api.messaging.packet.HeriaPacket;
import fr.heriamc.api.messaging.packet.HeriaPacketChannel;

public class InstanceStopPacket extends HeriaPacket {

    private final String name;

    public InstanceStopPacket(String name) {
        super(HeriaPacketChannel.QUEUE);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
