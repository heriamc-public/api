package fr.heriamc.bukkit.chat;

import fr.heriamc.api.data.CacheDataManager;
import fr.heriamc.api.data.redis.RedisConnection;
import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.chat.command.ReportChatCommand;
import fr.heriamc.bukkit.chat.cooldown.ChatCooldownListener;
import fr.heriamc.bukkit.chat.cooldown.ChatCooldownManager;
import org.checkerframework.checker.units.qual.C;

import java.util.UUID;

public class HeriaChatManager extends CacheDataManager<UUID, HeriaChatMessage> {

    private final HeriaBukkit bukkit;
    private final ChatCooldownManager cooldownManager;

    public HeriaChatManager(RedisConnection redisConnection, HeriaBukkit bukkit) {
        super(redisConnection, "chat");
        this.bukkit = bukkit;

        this.cooldownManager = new ChatCooldownManager();

        bukkit.getServer().getPluginManager().registerEvents(new HeriaChatListener(bukkit), bukkit);
        bukkit.getServer().getPluginManager().registerEvents(new ChatCooldownListener(this), bukkit);
        bukkit.getCommandManager().registerCommand(new ReportChatCommand(bukkit));
    }

    public HeriaBukkit getBukkit() {
        return bukkit;
    }

    public ChatCooldownManager getCooldownManager() {
        return cooldownManager;
    }
}
