package fr.heriamc.api.server;

import fr.heriamc.api.data.CacheDataManager;
import fr.heriamc.api.data.redis.RedisConnection;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HeriaServerManager extends CacheDataManager<String, HeriaServer> {

    public HeriaServerManager(RedisConnection redisConnection) {
        super(redisConnection, "servers");
    }

    public List<HeriaServer> getAll(HeriaServerType type){
        return this.getAllInCache().stream()
                .filter(heriaServer -> heriaServer.getType() == type)
                .collect(Collectors.toList());
    }

    public HeriaServer getWithLessPlayers(HeriaServerType type){
        return this.getAll(type).stream()
                .min(Comparator.comparingInt(HeriaServer::getConnectedCount))
                .orElse(null);
    }

    public int getAllPlayersOnServerType(HeriaServerType type) {
        List<HeriaServer> serverListByType = getAll(type);

        int a = 0;
        for(HeriaServer server : serverListByType){
            a += server.getConnected().size();
        }

        return a;
    }

    public HeriaServer getReadyWithLessPlayers(HeriaServerType type){
        return this.getAll(type).stream()
                .filter(heriaServer -> heriaServer.getStatus().isReachable())
                .min(Comparator.comparingInt(HeriaServer::getConnectedCount))
                .orElse(null);
    }

}
