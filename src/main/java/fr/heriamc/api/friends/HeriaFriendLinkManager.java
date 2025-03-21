package fr.heriamc.api.friends;

import fr.heriamc.api.data.PersistentDataManager;
import fr.heriamc.api.data.mongo.MongoConnection;
import fr.heriamc.api.data.redis.RedisConnection;

import java.util.List;
import java.util.UUID;

public class HeriaFriendLinkManager extends PersistentDataManager<UUID, HeriaFriendLink> {
    public HeriaFriendLinkManager(RedisConnection redisConnection, MongoConnection mongoConnection) {
        super(redisConnection, mongoConnection, "friendlinks", "friendlinks");
    }

    @Override
    public HeriaFriendLink getDefault() {
        return new HeriaFriendLink(null, HeriaFriendLinkStatus.SENT, null, null, System.currentTimeMillis());
    }

    public List<HeriaFriendLink> getFromPlayer(UUID uuid){
        return this.getAllFromPersistent()
                .stream()
                .filter(heriaFriendLink -> heriaFriendLink.getReceiver().equals(uuid) || heriaFriendLink.getSender().equals(uuid))
                .toList();
    }

    public List<HeriaFriendLink> getActiveFromPlayer(UUID uuid){
        return getFromPlayer(uuid)
                .stream()
                .filter(heriaFriendLink -> heriaFriendLink.getStatus() == HeriaFriendLinkStatus.ACTIVE)
                .toList();
    }

    public List<HeriaFriendLink> getReceivedFromPlayer(UUID uuid) {
        return getFromPlayer(uuid)
                .stream()
                .filter(heriaFriendLink -> heriaFriendLink.getStatus() == HeriaFriendLinkStatus.SENT)
                .filter(heriaFriendLink -> heriaFriendLink.getReceiver() == uuid)
                .toList();
    }

}
