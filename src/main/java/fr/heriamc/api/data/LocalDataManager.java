package fr.heriamc.api.data;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public abstract class LocalDataManager<A, D> {

    protected final Cache<A, D> localData;

    public LocalDataManager() {
        this(new LocalDataExpiration(1L, TimeUnit.SECONDS));
    }

    public LocalDataManager(LocalDataExpiration expiration) {
        CacheBuilder<Object, Object> builder = CacheBuilder.newBuilder();
        builder.concurrencyLevel(50);

        if(expiration != null) builder.expireAfterWrite(expiration.duration(), expiration.unit());

        this.localData = builder.build();
    }

    public D getInLocal(A identifier){
        return this.localData.getIfPresent(identifier);
    }

    public void putInLocal(A identifier, D data){
        this.localData.put(identifier, data);
    }

    public void removeInLocal(A identifier){
        this.localData.invalidate(identifier);
    }

    public void clearLocal(){
        this.localData.cleanUp();
    }

    public ConcurrentMap<A, D> getLocalMap(){
        return this.localData.asMap();
    }


}
