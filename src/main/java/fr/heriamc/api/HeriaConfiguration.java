package fr.heriamc.api;

import fr.heriamc.api.data.mongo.MongoCredentials;
import fr.heriamc.api.data.redis.RedisCredentials;

public class HeriaConfiguration {

    private final MongoCredentials mongoCredentials;
    private final RedisCredentials redisCredentials;

    public HeriaConfiguration(MongoCredentials mongoCredentials, RedisCredentials redisCredentials) {
        this.mongoCredentials = mongoCredentials;
        this.redisCredentials = redisCredentials;
    }

    public MongoCredentials getMongoCredentials() {
        return mongoCredentials;
    }

    public RedisCredentials getRedisCredentials() {
        return redisCredentials;
    }
}
