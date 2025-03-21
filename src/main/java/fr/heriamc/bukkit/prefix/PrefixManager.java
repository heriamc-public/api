package fr.heriamc.bukkit.prefix;

import fr.heriamc.api.data.PersistentDataManager;
import fr.heriamc.api.data.mongo.MongoConnection;
import fr.heriamc.api.data.redis.RedisConnection;
import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.prefix.commands.AskPrefixCommand;
import fr.heriamc.bukkit.prefix.commands.BrowsePrefixesCommand;

import java.util.Date;
import java.util.UUID;

public class PrefixManager extends PersistentDataManager<UUID, PrefixRequest> {

    private final HeriaBukkit bukkit;

    public PrefixManager(RedisConnection redisConnection, MongoConnection mongoConnection, HeriaBukkit bukkit) {
        super(redisConnection, mongoConnection, "prefixes", "prefixes");
        this.bukkit = bukkit;

        bukkit.getCommandManager().registerCommand(new AskPrefixCommand(bukkit));
        bukkit.getCommandManager().registerCommand(new BrowsePrefixesCommand(bukkit));
    }

    @Override
    public PrefixRequest getDefault() {
        return new PrefixRequest(null, null, "default-tag", new Date());
    }

    public PrefixRequest getFromPlayer(UUID player){
        return this.getAllFromPersistent()
                .stream()
                .filter(prefixRequest -> prefixRequest.getPlayer().equals(player))
                .findFirst().orElse(null);
    }
}
