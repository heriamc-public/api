package fr.heriamc.proxy.queue;

import fr.heriamc.api.messaging.packet.HeriaPacket;
import fr.heriamc.api.messaging.packet.HeriaPacketReceiver;
import fr.heriamc.proxy.queue.packet.QueueJoinPacket;
import fr.heriamc.proxy.queue.packet.QueueLeavePacket;

public class HeriaQueueReceiver implements HeriaPacketReceiver {

    private final ProxyQueueManager queueManager;

    public HeriaQueueReceiver(ProxyQueueManager queueManager) {
        this.queueManager = queueManager;
    }

    @Override
    public void execute(String channel, HeriaPacket packet) {
        if(packet instanceof QueueJoinPacket found){
            queueManager.onJoin(found);
        } else if(packet instanceof QueueLeavePacket found){
            queueManager.onLeave(found);
        }
    }
}
