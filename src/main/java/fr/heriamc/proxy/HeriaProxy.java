package fr.heriamc.proxy;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import fr.heriamc.api.HeriaAPI;
import fr.heriamc.api.HeriaConfiguration;
import fr.heriamc.api.friends.HeriaFriendLink;
import fr.heriamc.api.messaging.packet.HeriaPacketChannel;
import fr.heriamc.api.server.HeriaServer;
import fr.heriamc.api.server.HeriaServerType;
import fr.heriamc.api.user.HeriaPlayer;
import fr.heriamc.api.user.resolver.HeriaPlayerResolver;
import fr.heriamc.api.utils.GsonUtils;
import fr.heriamc.api.utils.HeriaFileUtils;
import fr.heriamc.proxy.listeners.ProxyPingListener;
import fr.heriamc.proxy.listeners.ProxyPlayerListener;
import fr.heriamc.proxy.listeners.ProxyServerListener;
import fr.heriamc.proxy.packet.ProxyPacketReceiver;
import fr.heriamc.proxy.pool.HeriaPoolManager;
import fr.heriamc.proxy.queue.ProxyQueueManager;
import org.slf4j.Logger;

import java.io.File;
import java.nio.file.Path;

@Plugin(
        id = "heriamc",
        name = "HeriaAPI",
        authors = {"Karaam_"},
        version = "1.0.0"
)
public class HeriaProxy {

    private final ProxyServer server;
    private final Logger logger;
    private final Path dataDirectory;

    private static HeriaProxy instance;
    private HeriaAPI api;

    private HeriaPoolManager poolManager;
    private ProxyQueueManager queueManager;

    @Inject
    public HeriaProxy(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory) {
        instance = this;

        this.server = server;
        this.logger = logger;
        this.dataDirectory = dataDirectory;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event){
        instance = this;
        this.dataDirectory.toFile().mkdir();

        File configFile = new File(this.dataDirectory.toFile(), "config.json");
        HeriaConfiguration config = HeriaFileUtils.fromJsonFile(configFile, GsonUtils.get(), HeriaConfiguration.class);

        if(config == null){
            this.logger.error("Error while loading the configuration file. is it present ?");
            this.server.shutdown();
            return;
        }

        this.api = HeriaAPI.createHeriaAPI(config);
        this.api.getMessaging().registerReceiver(HeriaPacketChannel.API, new ProxyPacketReceiver(this));

        this.poolManager = new HeriaPoolManager(this);
        this.queueManager = new ProxyQueueManager(this);

        this.server.getEventManager().register(this, new ProxyPlayerListener(this));
        this.server.getEventManager().register(this, new ProxyServerListener(this));
        this.server.getEventManager().register(this, new ProxyPingListener(this));

        this.poolManager.getServerPool(HeriaServerType.HUB);
        //this.api.getServerCreator().createServer(HeriaServerType.HUB, null);
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event){
        if(this.api != null){
            int serverCount = 0;

            this.poolManager.stopMakingServers();
            this.queueManager.killAll();

            for (HeriaServer heriaServer : this.api.getServerManager().getAllInCache()) {
                this.api.getServerCreator().deleteServer(heriaServer.getName());
                this.api.getServerManager().remove(heriaServer.getName());
                serverCount++;
            }

            for (HeriaPlayerResolver resolver : this.api.getResolverManager().getAllInCache()) {
                this.api.getResolverManager().remove(resolver.getIdentifier());
            }

            for (HeriaPlayer heriaPlayer : this.api.getPlayerManager().getAllInCache()) {
                this.api.getPlayerManager().remove(heriaPlayer.getIdentifier());
                System.out.println(heriaPlayer.getIdentifier() + " a été retiré du cache");
            }

            for (HeriaFriendLink heriaFriendLink : this.api.getFriendLinkManager().getAllInCache()) {
                this.api.getFriendLinkManager().saveInPersistant(heriaFriendLink);
                this.api.getFriendLinkManager().remove(heriaFriendLink.getIdentifier());
            }

            try {
                Thread.sleep(serverCount * 500L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            this.api.getRedisConnection().getResource().flushAll();
            this.api.getRedisConnection().getResource().scriptFlush();
            this.api.getRedisConnection().getResource().flushDB();
            this.api.onDisable();
        }
    }

    public static HeriaProxy get() {
        return instance;
    }

    public ProxyServer getServer() {
        return server;
    }

    public Logger getLogger() {
        return logger;
    }

    public Path getDataDirectory() {
        return dataDirectory;
    }

    public HeriaPoolManager getPoolManager() {
        return poolManager;
    }

    public ProxyQueueManager getQueueManager() {
        return queueManager;
    }

    public static HeriaProxy getInstance() {
        return instance;
    }

    public HeriaAPI getApi() {
        return api;
    }

}
