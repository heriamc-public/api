package fr.heriamc.api;

import fr.heriamc.api.data.mongo.MongoConnection;
import fr.heriamc.api.data.redis.RedisConnection;
import fr.heriamc.api.friends.HeriaFriendLinkManager;
import fr.heriamc.api.game.HeriaGameManager;
import fr.heriamc.api.group.HeriaGroupManager;
import fr.heriamc.api.messaging.HeriaMessaging;
import fr.heriamc.api.messaging.packet.HeriaPacketChannel;
import fr.heriamc.api.queue.HeriaQueue;
import fr.heriamc.api.queue.HeriaQueueManager;
import fr.heriamc.api.sanction.HeriaSanctionManager;
import fr.heriamc.api.server.HeriaServerManager;
import fr.heriamc.api.server.creator.HeriaServerCreator;
import fr.heriamc.api.user.HeriaPlayerManager;
import fr.heriamc.api.user.resolver.HeriaPlayerResolverManager;
import fr.heriamc.api.user.settings.HeriaPlayerSettingsManager;
import fr.heriamc.api.user.unlock.HeriaUnlockableManager;
import fr.heriamc.api.web.WebCreditsPacket;
import fr.heriamc.api.web.WebListener;
import redis.clients.jedis.JedisPool;

public class HeriaAPI {

    private static HeriaAPI instance;

    private final HeriaConfiguration configuration;

    private final MongoConnection mongoConnection;
    private final RedisConnection redisConnection;

    private final HeriaMessaging heriaMessaging;
    private final HeriaPlayerManager playerManager;
    private final HeriaServerManager serverManager;
    private final HeriaSanctionManager sanctionManager;
    private final HeriaServerCreator serverCreator;
    private final HeriaUnlockableManager unlockableManager;
    private final HeriaPlayerResolverManager resolverManager;
    private final HeriaFriendLinkManager friendLinkManager;
    private final HeriaGameManager heriaGameManager;
    private final HeriaGroupManager groupManager;
    private final HeriaQueueManager queueManager;
    private final HeriaPlayerSettingsManager settingsManager;

    public HeriaAPI(HeriaConfiguration configuration) {
        instance = this;

        this.configuration = configuration;
        this.mongoConnection = new MongoConnection(configuration.getMongoCredentials());
        this.redisConnection = new RedisConnection(configuration.getRedisCredentials(), 0);

        this.heriaMessaging = new HeriaMessaging(this.redisConnection);
        this.playerManager = new HeriaPlayerManager(this.redisConnection, this.mongoConnection);
        this.serverManager = new HeriaServerManager(this.redisConnection);
        this.serverCreator = new HeriaServerCreator(this.serverManager);
        this.sanctionManager = new HeriaSanctionManager(this.redisConnection, this.mongoConnection);
        this.unlockableManager = new HeriaUnlockableManager(this.redisConnection, this.mongoConnection);
        this.resolverManager = new HeriaPlayerResolverManager(this.redisConnection, this.mongoConnection);
        this.friendLinkManager = new HeriaFriendLinkManager(this.redisConnection, this.mongoConnection);
        this.heriaGameManager = new HeriaGameManager(this.redisConnection);
        this.groupManager = new HeriaGroupManager(this.redisConnection);
        this.queueManager = new HeriaQueueManager(this.redisConnection);
        this.settingsManager = new HeriaPlayerSettingsManager(this.redisConnection, this.mongoConnection);

        //this.heriaMessaging.registerReceiver(HeriaPacketChannel.WEB, new WebListener(this));
        //this.heriaMessaging.send(new WebCreditsPacket("Karaam_", 15.50F));
    }

    public void onDisable(){
        this.heriaMessaging.stop();

        JedisPool pool = this.redisConnection.getPool();
        pool.close();
        pool.destroy();
    }

    public static HeriaAPI createHeriaAPI(HeriaConfiguration configuration){
        return new HeriaAPI(configuration);
    }

    public static HeriaAPI get() {
        return instance;
    }

    public HeriaConfiguration getConfiguration() {
        return configuration;
    }

    public MongoConnection getMongoConnection() {
        return mongoConnection;
    }

    public RedisConnection getRedisConnection() {
        return redisConnection;
    }

    public HeriaMessaging getMessaging() {
        return heriaMessaging;
    }

    public HeriaPlayerManager getPlayerManager() {
        return playerManager;
    }

    public HeriaServerManager getServerManager() {
        return serverManager;
    }

    public HeriaSanctionManager getSanctionManager() {
        return sanctionManager;
    }

    public HeriaServerCreator getServerCreator() {
        return serverCreator;
    }

    public HeriaUnlockableManager getUnlockableManager() {
        return unlockableManager;
    }

    public HeriaPlayerResolverManager getResolverManager() {
        return resolverManager;
    }

    public HeriaFriendLinkManager getFriendLinkManager() {
        return friendLinkManager;
    }

    public HeriaGameManager getHeriaGameManager() {
        return heriaGameManager;
    }

    public HeriaGroupManager getGroupManager() {
        return groupManager;
    }

    public HeriaQueueManager getQueueManager() {
        return queueManager;
    }

    public HeriaPlayerSettingsManager getSettingsManager() {
        return settingsManager;
    }
}