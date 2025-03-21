package fr.heriamc.proxy.pool;

import fr.heriamc.api.messaging.packet.HeriaPacketChannel;
import fr.heriamc.api.server.HeriaServerType;
import fr.heriamc.api.game.size.GameSize;
import fr.heriamc.proxy.HeriaProxy;
import fr.heriamc.proxy.pool.types.GameServerPool;
import fr.heriamc.proxy.pool.types.ServerPool;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class HeriaPoolManager {

    private final HeriaProxy proxy;
    private final Set<HeriaPool> pools = ConcurrentHashMap.newKeySet();

    public HeriaPoolManager(HeriaProxy proxy) {
        this.proxy = proxy;
    }

    public HeriaPool getServerPool(HeriaServerType type){
        for (HeriaPool pool : pools) {
            if(pool.getServerType() == type && pool.getGameSize() == null){
                return pool;
            }
        }

        ServerPool serverPool = new ServerPool(proxy, type);
        pools.add(serverPool);
        this.proxy.getApi().getMessaging().registerReceiver(HeriaPacketChannel.QUEUE, serverPool);
        return serverPool;
    }

    public HeriaPool getGamePool(HeriaServerType type, GameSize gameSize){
        for (HeriaPool pool : pools) {
            if(pool.getServerType() == type && pool.getGameSize() == gameSize){
                return pool;
            }
        }

        GameServerPool gamePool = new GameServerPool(proxy, type, gameSize);
        pools.add(gamePool);
        this.proxy.getApi().getMessaging().registerReceiver(HeriaPacketChannel.GAME, gamePool);
        this.proxy.getApi().getMessaging().registerReceiver(HeriaPacketChannel.QUEUE, gamePool);
        return gamePool;
    }

    public void stopMakingServers(){
        for (HeriaPool pool : this.pools) {
            pool.stop();
        }
    }


}
