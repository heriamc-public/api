package fr.heriamc.api.queue;

import fr.heriamc.api.data.CacheDataManager;
import fr.heriamc.api.data.redis.RedisConnection;

import java.util.UUID;

public class HeriaQueueManager extends CacheDataManager<UUID, HeriaQueue> {
    public HeriaQueueManager(RedisConnection redisConnection) {
        super(redisConnection, "queues");
    }
}
