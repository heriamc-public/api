package fr.heriamc.bukkit.mod.freeze;

import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.utils.Title;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.WeakHashMap;

public class FreezeTask implements Runnable {

    private final WeakHashMap<Player, Location> freezedPlayers;
    
    public FreezeTask(HeriaBukkit heriaBukkit) {
        this.freezedPlayers = new WeakHashMap<>(6);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(heriaBukkit, this::run, 0L, 2L);
    }
    
    @Override
    public void run() {
        for (Map.Entry<Player, Location> freezedPlayer : freezedPlayers.entrySet()) {
            Player player = freezedPlayer.getKey();
            Location location = freezedPlayer.getValue();
            Location actualLoc = player.getLocation();

            if (player == null || !player.isOnline()) return;

            player.sendMessage("§cVous êtes freeze !");
            Title.sendTitle(player, 20, 20, 20, "§cATTENTION", "§cVous êtes freeze !!!!!!!");

            if (!actualLoc.equals(location)) {
                player.teleport(location);
                return;
            }
        }
    }

    public Map<Player, Location> getFreezedPlayers() {
        return freezedPlayers;
    }

}