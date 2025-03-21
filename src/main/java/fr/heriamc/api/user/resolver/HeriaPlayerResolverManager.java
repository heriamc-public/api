package fr.heriamc.api.user.resolver;

import fr.heriamc.api.data.PersistentDataManager;
import fr.heriamc.api.data.mongo.MongoConnection;
import fr.heriamc.api.data.redis.RedisConnection;

public class HeriaPlayerResolverManager extends PersistentDataManager<String, HeriaPlayerResolver> {

    public HeriaPlayerResolverManager(RedisConnection redisConnection, MongoConnection mongoConnection) {
        super(redisConnection, mongoConnection, "uuidResolver", "uuidResolver");
    }

    @Override
    public HeriaPlayerResolver getDefault() {
        return new HeriaPlayerResolver(null, null);
    }

    @Override
    public HeriaPlayerResolver get(String identifier) {
        return super.get(identifier.toLowerCase());
    }

}
