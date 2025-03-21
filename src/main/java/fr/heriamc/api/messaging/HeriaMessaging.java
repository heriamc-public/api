package fr.heriamc.api.messaging;

import fr.heriamc.api.data.redis.RedisConnection;
import fr.heriamc.api.messaging.packet.HeriaPacket;
import fr.heriamc.api.messaging.packet.HeriaPacketReceiver;
import fr.heriamc.api.messaging.packet.HeriaPacketSerializer;
import fr.heriamc.api.utils.GsonUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HeriaMessaging {

    private static final String CHANNEL_PREFIX = "heriamc@";

    private final RedisConnection redisConnection;
    private final HeriaSubscriber subscriber;

    private Thread subscriberThread;

    private final Map<String, Set<HeriaPacketReceiver>> receivers = new HashMap<>(0);


    public HeriaMessaging(RedisConnection redisConnection) {
        this.redisConnection = redisConnection;

        GsonUtils.registerSerializer(HeriaPacket.class, new HeriaPacketSerializer());
        this.subscriber = new HeriaSubscriber(this);
        this.start();
    }

    public void registerReceiver(String channel, HeriaPacketReceiver receiver) {
        Set<HeriaPacketReceiver> receivers = this.receivers.get(channel) != null ? this.receivers.get(channel) : new HashSet<>(0);
        receivers.add(receiver);

        this.receivers.put(channel, receivers);
    }

    public void start() {
        this.subscriberThread = new Thread(() -> {
            while (true) {
                this.redisConnection.process(jedis -> jedis.psubscribe(this.subscriber, CHANNEL_PREFIX + "*"));
            }
        });
        this.subscriberThread.start();
    }

    public void stop() {
        this.subscriberThread.interrupt();

        if (this.subscriber.isSubscribed()) {
            this.subscriber.punsubscribe();
        }
    }

    public void send(HeriaPacket packet) {
        System.out.println("packet channel = " + CHANNEL_PREFIX + packet.getChannel());
        System.out.println("packet content = " + GsonUtils.get().toJson(packet));
        this.redisConnection.process(jedis -> jedis.publish(CHANNEL_PREFIX + packet.getChannel(), GsonUtils.get().toJson(packet)));
    }

    public Map<String, Set<HeriaPacketReceiver>> getReceivers() {
        return this.receivers;
    }

}
