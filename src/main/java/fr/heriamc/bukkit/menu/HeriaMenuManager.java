package fr.heriamc.bukkit.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class HeriaMenuManager implements Listener {

    private final Plugin plugin;
    private final Map<Player, HeriaMenu> inventories = new HashMap<>();

    public HeriaMenuManager(Plugin plugin) {
        this.plugin = plugin;
        this.init();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getInventory();

        if (this.inventories.containsKey(player)) {
            HeriaMenu menu = this.inventories.get(player);
            if (inv.getName().equals(menu.getInventory().getName())) {
                menu.onClick0(event);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDrag(InventoryDragEvent event){
        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getInventory();

        if (this.inventories.containsKey(player)) {
            HeriaMenu menu = this.inventories.get(player);
            if (inv.getName().equals(menu.getInventory().getName())) {
                menu.onDrag(event);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDrop(PlayerDropItemEvent event){
        Player player = event.getPlayer();
        Inventory inv = player.getOpenInventory().getTopInventory();

        if(this.inventories.containsKey(player)){
            HeriaMenu menu = this.inventories.get(player);
            if (inv.getName().equals(menu.getInventory().getName())) {
                menu.onDrop(event);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        Inventory inv = player.getOpenInventory().getTopInventory();

        if(this.inventories.containsKey(player)){
            HeriaMenu menu = this.inventories.get(player);
            if (inv.getName().equals(menu.getInventory().getName())) {
                menu.onClose(event);
            }

            this.inventories.remove(player);
        }
    }

    public void init() {
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
        this.plugin.getServer().getScheduler().runTaskTimer(this.plugin, new HeriaMenuRunnable(this), 0L, 20L);
    }

    public void open(HeriaMenu menu) {

        Inventory inv = menu.getInventory();
        menu.contents(inv);

        new BukkitRunnable() {

            @Override
            public void run() {
                menu.getPlayer().openInventory(inv);
                inventories.remove(menu.getPlayer());
                inventories.put(menu.getPlayer(), menu);
            }

        }.runTaskLater(this.plugin, 1);

    }

    public Map<Player, HeriaMenu> getInventory() {
        return this.inventories;
    }
}