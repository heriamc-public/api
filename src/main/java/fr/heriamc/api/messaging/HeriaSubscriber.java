package fr.heriamc.api.messaging;

import fr.heriamc.api.messaging.packet.HeriaPacket;
import fr.heriamc.api.messaging.packet.HeriaPacketReceiver;
import fr.heriamc.api.utils.GsonUtils;
import redis.clients.jedis.JedisPubSub;

import java.util.HashSet;
import java.util.Set;

public class HeriaSubscriber extends JedisPubSub {

    private final HeriaMessaging heriaMessaging;

    public HeriaSubscriber(HeriaMessaging heriaMessaging) {
        this.heriaMessaging = heriaMessaging;
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        try {
            HeriaPacket packet = GsonUtils.get().fromJson(message, HeriaPacket.class);

            if(packet == null){
                return;
            }

            Set<HeriaPacketReceiver> receivers = this.heriaMessaging.getReceivers().get(packet.getChannel());

            if (receivers != null) {
                new HashSet<>(receivers).forEach(receiver -> receiver.execute(channel, packet));
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
