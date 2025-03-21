package fr.heriamc.bukkit.mod.history;

import fr.heriamc.api.sanction.HeriaSanction;
import fr.heriamc.api.sanction.HeriaSanctionType;
import fr.heriamc.api.user.HeriaPlayer;
import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.menu.pagination.HeriaPaginationMenu;
import fr.heriamc.bukkit.utils.ItemBuilder;
import fr.heriamc.api.utils.TimeUtils;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HistoryMenu extends HeriaPaginationMenu<HeriaSanction> {

    private final HeriaBukkit bukkit;
    private final HeriaPlayer heriaPlayer;

    public HistoryMenu(Player player, HeriaBukkit bukkit, HeriaPlayer heriaPlayer) {
        super(player, "Historique de " + heriaPlayer.getName(), 54, false, List.of(10,11,12,13,14,15,16,
                19,20,21,22,23,24,25,
                28,29,30,31,32,33,34,
                37,38,39,40,41,42,43), () -> bukkit.getApi().getSanctionManager().getAllSanctions(heriaPlayer.getId()));

        this.bukkit = bukkit;
        this.heriaPlayer = heriaPlayer;
    }

    @Override
    public void inventory(Inventory inventory) {
        setBorder(inventory, DyeColor.RED.getWoolData());

        this.insertInteractItem(inventory, 48, new ItemBuilder(Material.DARK_OAK_DOOR_ITEM)
                .setName("§cRetour en arrière")
                .onClick(event -> {
                    getPlayer().closeInventory();
                }));
    }

    @Override
    protected ItemBuilder item(HeriaSanction data, int slot, int page) {
        Material material = Material.STONE;

        HeriaPlayer byPlayer = bukkit.getApi().getPlayerManager().get(data.getBy());

        if(data.getType() == HeriaSanctionType.BAN) material = Material.ANVIL;
        if(data.getType() == HeriaSanctionType.KICK) material = Material.PAINTING;
        if(data.getType() == HeriaSanctionType.MUTE) material = Material.PAPER;
        if(data.getType() == HeriaSanctionType.WARN) material = Material.BED;

        long expireTime = data.getWhen().toInstant().toEpochMilli() + data.getDuration() * 1000L;
        return new ItemBuilder(material).setName("§c" + data.getType().getName() + " #" + data.getIdentifier().toString().split("-")[0])
                .addLore(" ")
                .addLore("§8» §7Auteur: " + byPlayer.getRank().getPrefix() + byPlayer.getName())
                .addLore("§8» §7Date: §e" + formatMillis(data.getWhen()))
                .addLore("§8» §7Retiré: " + formatBoolean(data.isRemoved()))
                .addLore("§8» §7Expiré: " + formatBoolean(data.isExpired()))
                .addLore("§8» §7Expiration: §3" + TimeUtils.transformLongToFormatedDate(expireTime))
                .addLore("§8» §7Raison: \"§c" + data.getReason() + "§7\"")
                .addLore(" ");


    }

    public static String formatMillis(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH'h'mm");
        return formatter.format(date);
    }

    public static String formatBoolean(boolean bool){
        if(bool) return "§aOui";
        return "§cNon";
    }
}
