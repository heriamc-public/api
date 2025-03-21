package fr.heriamc.bukkit.prefix.menu;

import fr.heriamc.api.user.HeriaPlayer;
import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.menu.HeriaMenu;
import fr.heriamc.bukkit.prefix.PrefixRequest;
import fr.heriamc.bukkit.utils.ItemBuilder;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class PrefixTakeDecisionMenu extends HeriaMenu {

    private final HeriaBukkit bukkit;
    private final HeriaPlayer target;
    private final PrefixRequest request;

    public PrefixTakeDecisionMenu(Player player, HeriaBukkit bukkit, HeriaPlayer target, PrefixRequest request) {
        super(player, "name", 54, false);
        this.bukkit = bukkit;
        this.target = target;
        this.request = request;
    }

    @Override
    public void contents(Inventory inv) {
        setBorder(inv, DyeColor.RED.getData());

        inv.setItem(22, new ItemBuilder(Material.NAME_TAG).setName("§6Demande de prefix")
                .addLore("§8#" + request.getId().toString().split("-")[0])
                .addLore(" ")
                .addLore("§7Auteur: " + target.getName())
                .addLore("§7Préfixe: " + request.getTag().replaceAll("&", "§"))
                .addLore(" ")
                .build());

        this.insertInteractItem(inv, 30, new ItemBuilder(Material.INK_SACK, 1, DyeColor.LIME.getDyeData())
                .setName("§aAccepter")
                .addLore(" ")
                .addLore("§7Accepter et mettre le grade")
                .addLore("§7de §6" + target.getName() + " §7à " + request.getTag())
                .addLore(" ")
                .addLore("§6§l» §eClique: §fAccepter")
                .onClick(event -> {
                    bukkit.getPrefixManager().removeInPersistant(request);
                    target.setCustomPrefix(request.getTag());

                    bukkit.getApi().getPlayerManager().save(target);
                    bukkit.getMenuManager().open(new PrefixRequestsMenu(getPlayer(), bukkit));
                }));

        this.insertInteractItem(inv, 31, new ItemBuilder(Material.INK_SACK, 1, DyeColor.RED.getDyeData())
                .setName("§cRefuser")
                .addLore(" ")
                .addLore("§7Refuser le grade de §6" + target.getName())
                .addLore(" ")
                .addLore("§6§l» §eClique: §fRefuser")
                .onClick(event -> {
                    bukkit.getPrefixManager().removeInPersistant(request);

                    bukkit.getMenuManager().open(new PrefixRequestsMenu(getPlayer(), bukkit));
                }));

        this.insertInteractItem(inv, 32, new ItemBuilder(Material.ANVIL)
                .setName("§cSanctionner")
                .addLore(" ")
                .addLore("§7Refuser le grade de §6" + target.getName())
                .addLore("§7et le sanctionner.")
                .addLore(" ")
                .addLore("§6§l» §eClique: §fSanctionner")
                .onClick(event -> {

                }));
    }

}
