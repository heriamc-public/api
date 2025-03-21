package fr.heriamc.bukkit.prefix.menu;

import fr.heriamc.api.user.HeriaPlayer;
import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.menu.pagination.HeriaPaginationMenu;
import fr.heriamc.bukkit.prefix.PrefixRequest;
import fr.heriamc.bukkit.utils.ItemBuilder;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class PrefixRequestsMenu extends HeriaPaginationMenu<PrefixRequest> {

    private final HeriaBukkit bukkit;

    public PrefixRequestsMenu(Player player, HeriaBukkit bukkit) {
        super(player, "Parcourir les prefixes", 54, false,  List.of(10,11,12,13,14,15,16,
                19,20,21,22,23,24,25,
                28,29,30,31,32,33,34,
                37,38,39,40,41,42,43), () -> bukkit.getPrefixManager().getAllFromPersistent());
        this.bukkit = bukkit;
    }

    @Override
    public void inventory(Inventory inventory) {
        setBorder(inventory, DyeColor.RED.getData());

        this.insertInteractItem(inventory, 48, new ItemBuilder(Material.DARK_OAK_DOOR_ITEM)
                .setName("§cQuitter")
                .onClick(event -> {
                    getPlayer().closeInventory();
                }));
    }

    @Override
    protected ItemBuilder item(PrefixRequest data, int slot, int page) {
        HeriaPlayer asker = bukkit.getApi().getPlayerManager().get(data.getPlayer());

        return new ItemBuilder(Material.NAME_TAG).setName("§6Demande de prefix")
                .addLore("§8#" + data.getId().toString().split("-")[0])
                .addLore(" ")
                .addLore("§7Auteur: " + asker.getName())
                .addLore("§7Préfixe: " + data.getTag().replaceAll("&", "§"))
                .addLore(" ")
                .addLore("§6§l» §eClique: §fPrendre une décision")
                .onClick(event -> {
                    bukkit.getMenuManager().open(new PrefixTakeDecisionMenu(getPlayer(), bukkit, asker, data));
                });
    }
}
