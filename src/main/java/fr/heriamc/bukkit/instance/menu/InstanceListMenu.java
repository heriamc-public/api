package fr.heriamc.bukkit.instance.menu;

import fr.heriamc.api.server.HeriaServer;
import fr.heriamc.api.server.HeriaServerStatus;
import fr.heriamc.api.server.HeriaServerType;
import fr.heriamc.api.utils.EnumIncludeNullUtils;
import fr.heriamc.api.utils.HeriaSkull;
import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.menu.HeriaMenu;
import fr.heriamc.bukkit.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.util.*;

public class InstanceListMenu extends HeriaMenu {

    private final HeriaBukkit bukkit;

    private final int[] instancesSlots = new int[]{
            10,11,12,13,14,15,16,
            19,20,21,22,23,24,25,
            28,29,30,31,32,33,34
    };

    private final Map<Integer, HeriaServer> instances = new HashMap<>();

    private HeriaServerStatus statusFilter;
    private HeriaServerType typeFilter;

    public  InstanceListMenu(Player player, HeriaBukkit bukkit) {
        super(player, "Liste des instances", 54, true);
        this.bukkit = bukkit;
    }

    @Override
    public void contents(Inventory inv) {
        List<HeriaServer> cacheInstances = bukkit.getApi().getServerManager().getAllInCache();

        int iteration = 0;

        Set<HeriaServer> toRemove = new HashSet<>();
        for (HeriaServer instance : cacheInstances) {
            if(statusFilter != null){
                if(instance.getStatus() != statusFilter){
                    toRemove.add(instance);
                }
            }

            if(typeFilter != null){
                if(instance.getType() != typeFilter){
                    toRemove.add(instance);
                }
            }
        }

        for (HeriaServer server : toRemove) {
            cacheInstances.remove(server);
        }

        for (HeriaServer instance : cacheInstances) {
            int slot = instancesSlots[iteration];
            inv.setItem(slot, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3)
                    .setSkullURL(instance.getStatus().getSkull().getURL())
                    .setName(instance.getStatus().getColor() + instance.getName())
                    .addLore(" ")
                    .addLore(" §8» §7Nom: §e" + instance.getName())
                    .addLore(" §8» §7Type: §3" + instance.getType().getName())
                    .addLore(" §8» §7Statut: " + instance.getStatus().getColor().getColor() + instance.getStatus().getName())
                    .addLore(" §8» §7Host: §a" + instance.getHost())
                    .addLore(" §8» §7Crée le: §d" + formatMillis(instance.getCreation()))
                    .addLore(" §8» §7Connectés: §6" + instance.getConnected().size())
                    .addLore(" ")
                    .addLore("§6§l» §eClique: §fAfficher")
                    .build());

            instances.put(slot, instance);
            iteration++;
        }

        ItemStack voidd = new ItemBuilder(Material.STAINED_GLASS_PANE,1,(short) 7).setName("").build();
        for (int instancesSlot : instancesSlots) {
            if(inv.getItem(instancesSlot) == null){
                inv.setItem(instancesSlot, voidd);
            }
        }

        this.insertInteractItem(inv, 46, new ItemBuilder(Material.DARK_OAK_DOOR_ITEM).setName("§cQuitter")
                .onClick(event -> {
                    this.getPlayer().closeInventory();
                }));

        this.insertInteractItem(inv, 47, new ItemBuilder(Material.SLIME_BALL).setName("§aCréer un nouveau")
                .onClick(event -> {
                    bukkit.getMenuManager().open(new InstanceCreateMenu(getPlayer(), bukkit, this));
                }));

        HeriaServerStatus previousStatus = EnumIncludeNullUtils.getPrevious(statusFilter, HeriaServerStatus.values());
        HeriaServerStatus nextStatus = EnumIncludeNullUtils.getNext(statusFilter, HeriaServerStatus.values());

        this.insertInteractItem(inv, 48, new ItemBuilder(Material.PISTON_STICKY_BASE).setName("§eFiltre de statut")
                .addLore(" ")
                .addLore(" §7▲ Statut: " + this.getFormattedName(previousStatus, previousStatus == null ? null : previousStatus.getName()))
                .addLore(" §e■ Statut: " + this.getFormattedName(statusFilter, statusFilter == null ? null : statusFilter.getName()))
                .addLore(" §7▼ Statut: " + this.getFormattedName(nextStatus, nextStatus == null ? null : nextStatus.getName()))
                .addLore(" ")
                .onClick(event -> {
                    if(event.isLeftClick()){
                        this.statusFilter = nextStatus;
                    } else if(event.isRightClick()){
                        this.statusFilter = previousStatus;
                    }

                    this.updateMenu();
                }));

        HeriaServerType previousType = EnumIncludeNullUtils.getPrevious(typeFilter, HeriaServerType.values());
        HeriaServerType nextType = EnumIncludeNullUtils.getNext(typeFilter, HeriaServerType.values());

        this.insertInteractItem(inv, 49, new ItemBuilder(Material.ANVIL).setName("§6Filtre de type")
                .addLore(" ")
                .addLore(" §7▲ Statut: " + this.getFormattedName(previousType, previousType == null ? null : previousType.getName()))
                .addLore(" §e■ Statut: " + this.getFormattedName(typeFilter, typeFilter == null ? null : typeFilter.getName()))
                .addLore(" §7▼ Statut: " + this.getFormattedName(nextType, nextType == null ? null : nextType.getName()))
                .addLore(" ")
                .onClick(event -> {
                    if(event.isLeftClick()){
                        this.typeFilter = previousType;
                    } else if(event.isRightClick()){
                        this.typeFilter = nextType;
                    }

                    this.updateMenu();
                }));

        this.insertInteractItem(inv, 51, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3)
                .setName("§8« §7Page précédente")
                .setSkullURL(HeriaSkull.GRAY_BACKWARDS.getURL())
                .onClick(event -> {

                }));

        this.insertInteractItem(inv, 52, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3)
                .setName("§8» §7Page suivante")
                .setSkullURL(HeriaSkull.GRAY_FORWARD.getURL())
                .onClick(event -> {

                }));
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if(instances.keySet().contains(event.getSlot())){
            HeriaServer server = instances.get(event.getSlot());
            bukkit.getMenuManager().open(new InstanceInfoMenu(getPlayer(), bukkit, this, server));
        }
    }

    public String getFormattedName(Object o, String name){
        if(o == null){
            return "Tous";
        }

        return name;
    }

    public static String formatMillis(long millis) {
        Date date = new Date(millis);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM HH'h'mm");
        return formatter.format(date);
    }

}
