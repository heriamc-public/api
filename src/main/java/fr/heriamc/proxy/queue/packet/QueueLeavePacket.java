package fr.heriamc.proxy.queue.packet;

import fr.heriamc.api.messaging.packet.HeriaPacket;
import fr.heriamc.api.messaging.packet.HeriaPacketChannel;

import java.util.UUID;

public class QueueLeavePacket extends HeriaPacket {

    private final UUID player;
    private final UUID queueID;

    public QueueLeavePacket(UUID player, UUID queueID) {
        super(HeriaPacketChannel.QUEUE);
        this.player = player;
        this.queueID = queueID;
    }

    public UUID getPlayer() {
        return player;
    }

    public UUID getQueueID() {
        return queueID;
    }
}
