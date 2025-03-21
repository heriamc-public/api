package fr.heriamc.api.data.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.function.Consumer;

public class RedisConnection {

    private final RedisCredentials credentials;
    private final int database;

    private JedisPool jedisPool;

    public RedisConnection(RedisCredentials credentials, int database) {
        this.credentials = credentials;
        this.database = database;

        this.initConnection();
    }

    public void initConnection(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(5120);
        jedisPoolConfig.setMaxIdle(1000);
        jedisPoolConfig.setJmxEnabled(true);

        this.jedisPool = new JedisPool(jedisPoolConfig,
                this.credentials.ip(),
                this.credentials.port(),
                2000,
                this.credentials.password(),
                this.database);
    }

    public void close(){
        if(!this.jedisPool.isClosed()) this.jedisPool.close();
    }

    public void process(Consumer<? super Jedis> action) {
        try (Jedis jedis = this.getResource()) {
            if (jedis != null) {
                action.accept(jedis);
            }
        }
    }

    public JedisPool getPool() {
        return this.jedisPool;
    }

    public Jedis getResource(){
        return this.jedisPool.getResource();
    }
}
