package fr.heriamc.bukkit.tab;

import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.utils.Title;
import org.bukkit.entity.Player;

public class TabUpdater implements Runnable {

    private final HeriaBukkit heriaBukkit;

    private final String serverName;

    public TabUpdater(HeriaBukkit heriaBukkit) {
        this.heriaBukkit = heriaBukkit;

        this.serverName = heriaBukkit.getInstanceName();
    }

    @Override
    public void run() {
        for (Player player : heriaBukkit.getServer().getOnlinePlayers()) {
            Title.sendTabTitle(player, "\n§r§e§l» §6§lHERIAMC §e§l«\n§r §7Vous êtes connecté sur §e" + serverName + " \n§r",
                    "\n§r §fUn problème ? Demande à un §bAssistant\n§r §fVous suspectez un joueur de triche ? §c/report\n §r\n §7Boutique,Forum,Support sur\n§r§b§nplay.heria-mc.fr\n§r");
        }
    }
}
