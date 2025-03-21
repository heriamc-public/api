package fr.heriamc.api.user.unlock;

import fr.heriamc.api.data.PersistentDataManager;
import fr.heriamc.api.data.mongo.MongoConnection;
import fr.heriamc.api.data.redis.RedisConnection;

import java.util.HashMap;
import java.util.UUID;

public class HeriaUnlockableManager extends PersistentDataManager<UUID, HeriaUnlockable> {

    public HeriaUnlockableManager(RedisConnection redisConnection, MongoConnection mongoConnection) {
        super(redisConnection, mongoConnection, "unlockable", "unlockable");
    }

    @Override
    public HeriaUnlockable getDefault() {
        return new HeriaUnlockable(null, new HashMap<>());
    }

}
