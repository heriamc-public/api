package fr.heriamc.bukkit.utils.bossbar;

import fr.heriamc.bukkit.utils.bossbar.animation.BossBarAnimation;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class BossBarListener implements Listener {

    private final Plugin plugin;

    public BossBarListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        BossBarManager.removeBar(event.getPlayer());
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        final BossBar bossBar = BossBarManager.getBar(event.getPlayer());

        if (bossBar != null) {
            bossBar.updateMovement();
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onTeleport(PlayerTeleportEvent event) {
        this.handleTeleport(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onRespawn(PlayerRespawnEvent event) {
        this.handleTeleport(event.getPlayer());
    }

    private void handleTeleport(Player player) {
        final BossBar bossBar = BossBarManager.getBar(player);

        if (bossBar == null) {
            return;
        }

        BossBarManager.removeBar(player);

        new BukkitRunnable() {
            @Override
            public void run() {
                final BossBar newBar = BossBarManager.setBar(player, bossBar.getText(), bossBar.getProgress());
                final BossBarAnimation animation = bossBar.getAnimation();

                if (animation != null) {
                    animation.disable();

                    newBar.applyAnimation(animation);
                }
            }
        }.runTaskLaterAsynchronously(this.plugin, 2L);

    }
}
