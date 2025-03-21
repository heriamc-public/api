package fr.heriamc.bukkit.mod.staff;

import fr.heriamc.api.user.rank.HeriaRank;
import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.command.CommandArgs;
import fr.heriamc.bukkit.command.HeriaCommand;
import fr.heriamc.bukkit.packet.BukkitBroadcastMessagePacket;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class ChatStaffCommand {

    private final HeriaBukkit bukkit;

    public ChatStaffCommand(HeriaBukkit bukkit) {
        this.bukkit = bukkit;
    }

    @HeriaCommand(name = "chatstaff", power = HeriaRank.HELPER, aliases = {"cs"})
    public void onChatStaffCommand(CommandArgs args){
        Player player = args.getPlayer();

        if(args.getArgs().length < 1){
            player.sendMessage("§c/chatstaff <message>");
            return;
        }

        String message = String.join(" ", Arrays.copyOfRange(args.getArgs(), 0, args.getArgs().length));

        bukkit.getApi().getMessaging().send(new BukkitBroadcastMessagePacket("§e[§6Staff§e] " + player.getName() + ": §3" + message,
                HeriaRank.GRAPHIC.getPower()));
    }
}
