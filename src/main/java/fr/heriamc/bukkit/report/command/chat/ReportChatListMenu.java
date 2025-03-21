package fr.heriamc.bukkit.report.command.chat;

import fr.heriamc.api.user.HeriaPlayer;
import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.menu.pagination.HeriaPaginationMenu;
import fr.heriamc.bukkit.mod.sanction.SubSanctionMenu;
import fr.heriamc.bukkit.mod.sanction.UISanctionType;
import fr.heriamc.bukkit.report.HeriaReport;
import fr.heriamc.bukkit.report.HeriaReportType;
import fr.heriamc.bukkit.utils.ItemBuilder;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class ReportChatListMenu extends HeriaPaginationMenu<HeriaReport> {

    private final HeriaBukkit heriaBukkit;

    public ReportChatListMenu(Player player, HeriaBukkit heriaBukkit) {
        super(player, "Reports chat", 54, false, List.of(10,11,12,13,14,15,16,
                        19,20,21,22,23,24,25,
                        28,29,30,31,32,33,34,
                        37,38,39,40,41,42,43),
                () -> heriaBukkit.getReportManager().getAllReports(HeriaReportType.CHAT));

        this.heriaBukkit = heriaBukkit;
    }


    @Override
    public void inventory(Inventory inventory) {
        this.setBorder(inventory, DyeColor.RED.getWoolData());

        this.insertInteractItem(inventory, 48, new ItemBuilder(Material.DARK_OAK_DOOR_ITEM)
                .setName("§cRetour en arrière")
                .onClick(event -> {
                    getPlayer().closeInventory();
                }));
    }

    @Override
    protected ItemBuilder item(HeriaReport heriaReport, int slot, int page) {
        HeriaPlayer heriaSender = this.heriaBukkit.getApi().getPlayerManager().get(heriaReport.getSender());
        HeriaPlayer heriaTarget = this.heriaBukkit.getApi().getPlayerManager().get(heriaReport.getTarget());

        return new ItemBuilder(Material.BOOK_AND_QUILL)
                .setName("§6Report chat")
                .setLoreWithList("§8#" + heriaReport.getId().toString().split("-")[0],
                        " ",
                        "§7Signalé par: " + heriaSender.getRank().getPrefix() + heriaSender.getName(),
                        "§7Signalé: " + heriaTarget.getRank().getPrefix() + heriaTarget.getName(),
                        "§7Message: \"" + heriaReport.getReason() + "\"",
                        " ",
                        "§6&l❱ §eClique pour juger")
                .onClick(event -> {
                    SubSanctionMenu menu = new SubSanctionMenu(getPlayer(), heriaBukkit, heriaTarget, UISanctionType.CHAT, this);
                    menu.setHeriaReport(heriaReport);
                    this.heriaBukkit.getMenuManager().open(menu);
                });
    }
}
