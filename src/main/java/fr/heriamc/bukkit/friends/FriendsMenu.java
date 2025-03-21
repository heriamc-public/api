package fr.heriamc.bukkit.friends;

import fr.heriamc.api.friends.HeriaFriendLink;
import fr.heriamc.api.user.HeriaPlayer;
import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.menu.pagination.HeriaPaginationMenu;
import fr.heriamc.bukkit.utils.ItemBuilder;
import fr.heriamc.api.utils.TimeUtils;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Sound;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class FriendsMenu extends HeriaPaginationMenu<HeriaFriendLink> {

    private final HeriaBukkit bukkit;
    private final HeriaPlayer heriaPlayer;

    public FriendsMenu(Player player, HeriaBukkit bukkit, HeriaPlayer heriaPlayer) {
        super(player, "Vos amis", 54, true, List.of(10,11,12,13,14,15,16,
                19,20,21,22,23,24,25,
                28,29,30,31,32,33,34,
                37,38,39,40,41,42,43), () -> bukkit.getFriendLinkManager().getActiveFromPlayer(heriaPlayer.getId()));
        this.bukkit = bukkit;
        this.heriaPlayer = heriaPlayer;
    }

    @Override
    public void inventory(Inventory inventory) {
        setBorder(inventory, DyeColor.PINK.getData());

        this.insertInteractItem(inventory, 48, new ItemBuilder(Material.DARK_OAK_DOOR_ITEM)
                .setName("§c» Quitter")
                .onClick(event -> {
                    getPlayer().closeInventory();
                }));

        this.insertInteractItem(inventory, 35, new ItemBuilder(Material.ANVIL)
                .setName("§aDemandes reçues")
                .onClick(event -> {
                    bukkit.getMenuManager().open(new ReceivedRequestsMenu(getPlayer(), bukkit, heriaPlayer));
                }));


    }

    @Override
    protected ItemBuilder item(HeriaFriendLink data, int slot, int page) {
        HeriaPlayer other = bukkit.getApi().getPlayerManager().get(data.getOther(heriaPlayer.getId()));

        return new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3)
                .setName("§6" + other.getName())
                .addLore(" ")
                .addLore("§8» §7Connecté: " + (other.isConnected() ? "§a✔" : "§c✘"))
                .addLore("§8» §7Ami depuis: §6" + TimeUtils.convertMilliSecondsToFormattedDate(data.getInstant()))
                .addLore(" ")
                .addLore("§6§l» §eClique: §fRejoindre")
                .addLore("§6§l» §eJeter: §fSupprimer")
                .setSkullOwner(other.getName())
                .onClick(event -> {
                    if(event.getAction().name().contains("DROP")){

                        this.bukkit.getFriendLinkManager().remove(data);
                        this.bukkit.getFriendLinkManager().removeInPersistant(data);

                        this.updateMenu();
                        return;
                    }

                    if(!other.isConnected()){
                        getPlayer().playSound(getPlayer().getLocation(), Sound.VILLAGER_NO, 1, 1);
                    }

                    //TODO: add in queue to player game or server aidez moi dieu
                });
    }

}
