package fr.heriamc.api.group;

import fr.heriamc.api.data.CacheDataManager;
import fr.heriamc.api.data.redis.RedisConnection;

import java.util.UUID;

public class HeriaGroupManager extends CacheDataManager<UUID, HeriaGroup> {

    public HeriaGroupManager(RedisConnection redisConnection) {
        super(redisConnection, "playerGroups");
    }

}
