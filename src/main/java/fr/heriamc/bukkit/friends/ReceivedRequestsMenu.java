package fr.heriamc.bukkit.friends;

import fr.heriamc.api.friends.HeriaFriendLink;
import fr.heriamc.api.friends.HeriaFriendLinkStatus;
import fr.heriamc.api.user.HeriaPlayer;
import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.menu.pagination.HeriaPaginationMenu;
import fr.heriamc.bukkit.utils.ItemBuilder;
import fr.heriamc.api.utils.TimeUtils;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class ReceivedRequestsMenu extends HeriaPaginationMenu<HeriaFriendLink> {

    private final HeriaBukkit bukkit;
    private final HeriaPlayer heriaPlayer;

    public ReceivedRequestsMenu(Player player, HeriaBukkit bukkit, HeriaPlayer heriaPlayer) {
        super(player, "Demandes reçues", 54, true, List.of(10,11,12,13,14,15,16,
                19,20,21,22,23,24,25,
                28,29,30,31,32,33,34,
                37,38,39,40,41,42,43), () -> bukkit.getFriendLinkManager().getReceivedFromPlayer(player.getUniqueId()));
        this.bukkit = bukkit;
        this.heriaPlayer = heriaPlayer;
    }

    @Override
    public void inventory(Inventory inventory) {
        setBorder(inventory, DyeColor.PINK.getData());

        this.insertInteractItem(inventory, 48, new ItemBuilder(Material.DARK_OAK_DOOR_ITEM)
                .setName("§c» Quitter")
                .onClick(event -> {
                    bukkit.getMenuManager().open(new FriendsMenu(getPlayer(), bukkit, heriaPlayer));
                }));

    }

    @Override
    protected ItemBuilder item(HeriaFriendLink data, int slot, int page) {
        HeriaPlayer sender = bukkit.getApi().getPlayerManager().get(data.getSender());

        return new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3)
                .setName("§d" + sender.getName())
                .addLore(" ")
                .addLore("Demandé le: §6" + TimeUtils.convertMilliSecondsToFormattedDate(data.getInstant()))
                .addLore(" ")
                .addLore("§6§l» §eClique: §fAjouter")
                .addLore("§6§l» §eJeter: §fRefuser")
                .onClick(event -> {
                    if(event.getAction().name().contains("DROP")){

                        this.bukkit.getFriendLinkManager().remove(data);
                        this.bukkit.getFriendLinkManager().removeInPersistant(data);

                        this.updateMenu();
                        return;
                    }

                    data.setStatus(HeriaFriendLinkStatus.ACTIVE);
                    this.bukkit.getFriendLinkManager().save(data);
                    this.bukkit.getFriendLinkManager().saveInPersistant(data);
                    this.updateMenu();
                });
    }
}
