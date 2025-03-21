package fr.heriamc.bukkit.mod;

import fr.heriamc.api.user.HeriaPlayer;
import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.utils.ItemBuilder;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public enum ModItem {

    KB1(0, new ItemBuilder(Material.WOOD_SWORD).setName("§6Epée KB1")
            .setInfinityDurability().addEnchant(Enchantment.DAMAGE_ALL, 1).build()),

    KB5(1, new ItemBuilder(Material.WOOD_SWORD).setName("§6Epée KB5")
            .setInfinityDurability().addEnchant(Enchantment.DAMAGE_ALL, 5).build()),

    HISTORY(3, new ItemBuilder(Material.BOOK).setName("§eHistorique des sanctions").build()){
        @Override
        public void onInteractOnPlayer(HeriaBukkit bukkit, Player player, Player interacted) {
            player.performCommand("history " + interacted.getName());
        }
    },

    FREEZE(4, new ItemBuilder(Material.PACKED_ICE).setName("§bFreeze").build()){
        @Override
        public void onInteractOnPlayer(HeriaBukkit bukkit, Player player, Player interacted) {
            player.performCommand("freeze " + interacted.getName());
        }
    },

    SANCTION(5, new ItemBuilder(Material.ANVIL).setName("§cSanctionner").build()){
        @Override
        public void onInteractOnPlayer(HeriaBukkit bukkit, Player player, Player interacted) {
            player.performCommand("ss " + interacted.getName());
        }
    },

    INFORMATION(7, new ItemBuilder(Material.PAPER).setName("§aInformation").build()){
        @Override
        public void onInteractOnPlayer(HeriaBukkit bukkit, Player player, Player interacted) {
            player.performCommand("check " + interacted.getName());
        }
    },


    VANISH(8, new ItemBuilder(Material.INK_SACK, 1, DyeColor.LIME.getDyeData()).setName("§7Vanish: §aON").build()){

        private final ItemStack notVanished = new ItemBuilder(Material.INK_SACK, 1, DyeColor.GRAY.getDyeData()).setName("§7Vanish: §cOFF").build();

        @Override
        public boolean isSimilar(ItemStack other) {
            return other.getType() == Material.INK_SACK;
        }

        @Override
        public void onInteract(ModManager modManager, Player player) {
            HeriaPlayer heriaPlayer = modManager.bukkit().getApi().getPlayerManager().get(player.getUniqueId());

            player.getInventory().setItem(this.getSlot(), heriaPlayer.isVanished() ? notVanished : this.getItemStack());
            modManager.setVanished(player, heriaPlayer, !heriaPlayer.isVanished());
            modManager.bukkit().getApi().getPlayerManager().save(heriaPlayer);
        }

    }

    ;

    private final int slot;
    private final ItemStack itemStack;

    ModItem(int slot, ItemStack itemStack) {
        this.slot = slot;
        this.itemStack = itemStack;
    }

    public int getSlot() {
        return slot;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void onInteract(ModManager modManager, Player player){

    }

    public void onInteractOnPlayer(HeriaBukkit bukkit, Player player, Player interacted){

    }

    public boolean isSimilar(ItemStack other){
        return this.itemStack.equals(other);
    }

    public void setItem(Inventory inventory){
        inventory.setItem(this.getSlot(), this.getItemStack());
    }

    public static ModItem getFromStack(ItemStack stack){
        for (ModItem value : ModItem.values()) {
            if(value.isSimilar(stack)){
                return value;
            }
        }

        return null;
    }



}
