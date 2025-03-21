package fr.heriamc.bukkit.menu;

import fr.heriamc.bukkit.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public abstract class HeriaMenu implements InventoryHolder {

    private final Player player;
    private final String name;
    private final int size;
    private final InventoryType type;
    private final boolean update;

    private final Map<Integer, ItemBuilder> interactItems = new HashMap<>();

    public HeriaMenu(Player player, String name, int size, boolean update) {
        this.player = player;
        this.name = name;
        this.size = size;
        this.type = null;
        this.update = update;
    }

    public HeriaMenu(Player player, String name, InventoryType type, boolean update) {
        this.player = player;
        this.name = name;
        this.size = 0;
        this.type = type;
        this.update = update;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isUpdate() {
        return update;
    }

    public abstract void contents(Inventory inv);

    public void insertInteractItem(Inventory inv, int slot, ItemBuilder builder){
        inv.setItem(slot, builder.build());
        interactItems.put(slot, builder);
    }

    void onClick0(InventoryClickEvent event){
        ItemBuilder interactItem = this.interactItems.get(event.getSlot());
        if(interactItem != null) interactItem.getClickEvent().accept(event);
        event.setCancelled(true);

        this.onClick(event);
    }

    public void onClick(InventoryClickEvent event){

    }

    public void onDrag(InventoryDragEvent event){
    }

    public void onDrop(PlayerDropItemEvent event){

    }

    public void onClose(InventoryCloseEvent event){

    }


    public void setBorder(Inventory inv, int data){
        this.setBorder(inv, (short) data);
    }

    public void setBorder(Inventory inv, short data){
        int[] corners = getCorners(inv);

        ItemStack item = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, data).setName(" ").build();

        for (int glass : corners) {
            inv.setItem(glass, item);
        }
    }

    private int[] getCorners(Inventory inventory) {
        int size = inventory.getSize();
        return IntStream.range(0, size).filter(i -> i < 2 || (i > 6 && i < 10) || i == 17 || i == size - 18 || (i > size - 11 && i < size - 7) || i > size - 3).toArray();
    }

    public void updateMenu() {
        this.player.getOpenInventory().getTopInventory().clear();
        this.contents(this.player.getOpenInventory().getTopInventory());
    }

    public Inventory getInventory() {
        if (this.type == null) {
            return Bukkit.createInventory(this, this.size, this.name);
        } else {
            return Bukkit.createInventory(this, this.type, this.name);
        }
    }



}