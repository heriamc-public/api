package fr.heriamc.bukkit.report.command.chat;

import fr.heriamc.api.user.rank.HeriaRank;
import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.command.CommandArgs;
import fr.heriamc.bukkit.command.HeriaCommand;
import org.bukkit.entity.Player;

public class ReportChatListCommand {

    private final HeriaBukkit heriaBukkit;

    public ReportChatListCommand(HeriaBukkit heriaBukkit) {
        this.heriaBukkit = heriaBukkit;
    }

    @HeriaCommand(name = "rclist", power = HeriaRank.HELPER, inGameOnly = true)
    public void onRCList(CommandArgs args){
        Player player = args.getPlayer();

        heriaBukkit.getMenuManager().open(new ReportChatListMenu(player, heriaBukkit));
    }
}
