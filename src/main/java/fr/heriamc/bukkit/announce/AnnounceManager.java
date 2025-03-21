package fr.heriamc.bukkit.announce;

import fr.heriamc.api.data.PersistentDataManager;
import fr.heriamc.api.data.mongo.MongoConnection;
import fr.heriamc.api.data.redis.RedisConnection;
import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.announce.base.AnnounceBossBar;
import fr.heriamc.bukkit.announce.base.AnnounceCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AnnounceManager extends PersistentDataManager<UUID, HeriaAnnounce> implements Listener {

    private final HeriaBukkit bukkit;
    private final Map<UUID, AnnounceBossBar> bars = new HashMap<>();

    public AnnounceManager(RedisConnection redisConnection, MongoConnection mongoConnection, HeriaBukkit bukkit) {
        super(redisConnection, mongoConnection, "announces", "announces");
        this.bukkit = bukkit;
        this.bukkit.getServer().getPluginManager().registerEvents(this, bukkit);
        this.bukkit.getCommandManager().registerCommand(new AnnounceCommand(bukkit));
    }

    @Override
    public HeriaAnnounce getDefault() {
        return new HeriaAnnounce(null, System.currentTimeMillis(), null, "default message", null);
    }

    @Override
    public void onDataSave(HeriaAnnounce data) {
        if(this.getAllFromPersistent().size() == 1){
            Bukkit.getOnlinePlayers().forEach(this::initBar);
        }
    }

    public void initBar(Player player){
        AnnounceBossBar bar = this.bars.getOrDefault(player.getUniqueId(), new AnnounceBossBar(player, this));
        //Bukkit.broadcastMessage("bar created or loaded: " + bar);
        bar.init();
        //Bukkit.broadcastMessage("bar initted");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        //Bukkit.broadcastMessage("player " + event.getPlayer() + " joined, initting bar.");
        this.initBar(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        final AnnounceBossBar bar = this.bars.remove(player.getUniqueId());

        if (bar != null) {
            bar.remove();
        }
    }

    public HeriaBukkit getBukkit() {
        return bukkit;
    }
}
