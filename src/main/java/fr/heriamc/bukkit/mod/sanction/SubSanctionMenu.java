package fr.heriamc.bukkit.mod.sanction;

import fr.heriamc.api.user.HeriaPlayer;
import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.chat.HeriaChatMessage;
import fr.heriamc.bukkit.menu.HeriaMenu;
import fr.heriamc.bukkit.report.HeriaReport;
import fr.heriamc.bukkit.utils.ItemBuilder;
import fr.heriamc.api.utils.TimeUtils;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Date;

public class SubSanctionMenu extends HeriaMenu {

    private final HeriaBukkit heriaBukkit;
    private final HeriaPlayer target;
    private final UISanctionType sanctionType;
    private final HeriaMenu before;

    private HeriaChatMessage chatMessage;
    private HeriaReport report;

    private final int[] slots = new int[]{
            11,12,13,14,15,
            20,21,22,23,24,
            29,30,31,32,33,
            38,39,40,41,42
    };

    public SubSanctionMenu(Player player, HeriaBukkit heriaBukkit, HeriaPlayer target, UISanctionType sanctionType, HeriaMenu before) {
        super(player, "Sanction pour " + target.getName(), 54, false);
        this.heriaBukkit = heriaBukkit;
        this.target = target;
        this.sanctionType = sanctionType;
        this.before = before;
    }

    @Override
    public void contents(Inventory inv) {
        setBorder(inv, DyeColor.RED.getWoolData());

        int index = 0;
        for (UISanctionSubType value : UISanctionSubType.values()) {
            if(value.getType() != sanctionType){
                continue;
            }

            if(value == UISanctionSubType.ABUS) continue;

            long expireTime = new Date().toInstant().toEpochMilli() + value.getDuration() * 1000L;
            this.insertInteractItem(inv, slots[index], new ItemBuilder(value.getMaterial())
                    .setName("§c" + value.getInternBanReason())
                    .addLore(" ")
                    .addLore("§8» §7Durée: §f" + TimeUtils.transformLongToFormatedDate(expireTime))
                    .addLore("§8» §7Raison: §f" + value.getBanReason())
                    .addLore("§8» §7Type: §f" + value.getSanctionType().name().toUpperCase())
                    .addLore(" ")
                    .addLore("§6§l» §eClique: §fSanctionner")
                    .onClick(event -> {
                        heriaBukkit.getMenuManager().open(new SanctionConfirmMenu(getPlayer(), heriaBukkit, this, target, value, chatMessage, report));
                    }));

            index++;
        }

        //TODO: sanctionner un abus

        this.insertInteractItem(inv, 49, new ItemBuilder(Material.DARK_OAK_DOOR_ITEM)
                .setName("§cRetour en arrière")
                .onClick(event -> {
                    if(before != null){
                        heriaBukkit.getMenuManager().open(before);
                        return;
                    }

                    getPlayer().closeInventory();
                }));
    }

    public HeriaChatMessage getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(HeriaChatMessage chatMessage) {
        this.chatMessage = chatMessage;
    }

    public HeriaReport getHeriaReport() {
        return report;
    }

    public void setHeriaReport(HeriaReport heriaReport) {
        this.report = heriaReport;
    }

}
