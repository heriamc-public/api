package fr.heriamc.bukkit.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class HeriaMenuRunnable implements Runnable {

    private final HeriaMenuManager menuManager;

    public HeriaMenuRunnable(HeriaMenuManager menuManager) {
        this.menuManager = menuManager;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (this.menuManager.getInventory().containsKey(player)) {
                if (this.menuManager.getInventory().get(player).isUpdate()) {
                    this.menuManager.getInventory().get(player).updateMenu();
                }
            }
        }
    }
}