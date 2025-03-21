package fr.heriamc.bukkit.instance.menu;

import fr.heriamc.api.server.HeriaServerType;
import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.instance.InstanceConnectRunnable;
import fr.heriamc.bukkit.menu.HeriaMenu;
import fr.heriamc.bukkit.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public class InstanceCreateMenu extends HeriaMenu {

    private final HeriaBukkit heriaBukkit;
    private final HeriaMenu before;
    private final Map<Integer, HeriaServerType> serverTypes = new HashMap<>();

    public InstanceCreateMenu(Player player, HeriaBukkit heriaBukkit, HeriaMenu before) {
        super(player, "Créer une instance", 54, false);
        this.heriaBukkit = heriaBukkit;
        this.before = before;
    }

    @Override
    public void contents(Inventory inv) {
        int i = 0;
        for (HeriaServerType value : HeriaServerType.values()) {
            inv.setItem(i, new ItemBuilder(Material.NAME_TAG).setName(value.getName()).build());
            this.serverTypes.put(i, value);
            i++;
        }

        this.insertInteractItem(inv, i, new ItemBuilder(Material.DARK_OAK_DOOR_ITEM).setName("§cQuitter").onClick(event -> {
            heriaBukkit.getMenuManager().open(before);
        }));
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if(serverTypes.keySet().contains(event.getSlot())){
            HeriaServerType serverType = this.serverTypes.get(event.getSlot());
            heriaBukkit.getMenuManager().open(new InstanceCreateConfirmMenu(getPlayer(), heriaBukkit, serverType, this.before));
        }
    }

    public static class InstanceCreateConfirmMenu extends HeriaMenu {

        private final HeriaBukkit bukkit;
        private final HeriaServerType serverType;
        private final HeriaMenu before;

        public InstanceCreateConfirmMenu(Player player, HeriaBukkit bukkit, HeriaServerType serverType, HeriaMenu before) {
            super(player, "Se téléporter à votre serveur ?", 27, false);
            this.bukkit = bukkit;
            this.serverType = serverType;
            this.before = before;
        }

        @Override
        public void contents(Inventory inv) {
            this.insertInteractItem(inv, 11, new ItemBuilder(Material.SLIME_BALL).setName("§aOui")
                    .onClick(event -> {
                        this.getPlayer().closeInventory();
                        String name = this.bukkit.getApi().getServerCreator().createServer(serverType, null);
                        InstanceConnectRunnable connectRunnable = new InstanceConnectRunnable(bukkit, getPlayer(), name);
                        connectRunnable.runTaskTimer(bukkit, 0L, 3L);
                    }));

            this.insertInteractItem(inv, 15, new ItemBuilder(Material.REDSTONE).setName("§cNon")
                    .onClick(event -> {
                        this.bukkit.getApi().getServerCreator().createServer(serverType, null);
                        bukkit.getMenuManager().open(before);
                    }));
        }
    }
}
