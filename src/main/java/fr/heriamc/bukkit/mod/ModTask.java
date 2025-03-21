package fr.heriamc.bukkit.mod;

import fr.heriamc.api.user.HeriaPlayer;
import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.utils.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ModTask implements Runnable{

    private final HeriaBukkit bukkit;

    public ModTask(HeriaBukkit bukkit) {
        this.bukkit = bukkit;
    }

    @Override
    public void run() {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            HeriaPlayer heriaOnline = bukkit.getApi().getPlayerManager().get(onlinePlayer.getUniqueId());

            if(heriaOnline.isMod()){
                Title.sendActionBar(onlinePlayer, "§dMode Modération Activé.");
            }
        }
    }
}
