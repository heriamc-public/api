package fr.heriamc.bukkit.mod.staff;

import fr.heriamc.api.user.HeriaPlayer;
import fr.heriamc.api.user.rank.HeriaRank;
import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.command.CommandArgs;
import fr.heriamc.bukkit.command.HeriaCommand;
import org.bukkit.command.CommandSender;

import java.util.List;

public class StaffCommand {

    private final HeriaBukkit heriaBukkit;

    public StaffCommand(HeriaBukkit heriaBukkit) {
        this.heriaBukkit = heriaBukkit;
    }

    @HeriaCommand(name = "staff", power = HeriaRank.GRAPHIC)
    public void onStaffCommand(CommandArgs args){
        CommandSender sender = args.getSender();

        List<HeriaPlayer> allInCache = this.heriaBukkit.getApi().getPlayerManager().getAllInCache();

        sender.sendMessage(" ");
        sender.sendMessage("§7Liste des staff en ligne:");
        for (HeriaPlayer heriaPlayer : allInCache) {
            if(!heriaPlayer.isConnected()){
                continue;
            }

            if(heriaPlayer.getRank().getPower() < HeriaRank.GRAPHIC.getPower()){
                continue;
            }

            sender.sendMessage("§a・ " + heriaPlayer.getRank().getPrefix() + heriaPlayer.getName() + "§f: §e" + heriaPlayer.getConnectedTo()
            + (heriaPlayer.isMod() ? " §8(§6en /mod§8)" : "" ) + (heriaPlayer.isVanished() ? " §8(§dvanish§8)" : ""));
        }
        sender.sendMessage(" ");
    }
}
