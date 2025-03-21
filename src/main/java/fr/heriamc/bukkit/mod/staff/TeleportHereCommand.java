package fr.heriamc.bukkit.mod.staff;

import fr.heriamc.api.user.rank.HeriaRank;
import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.command.CommandArgs;
import fr.heriamc.bukkit.command.HeriaCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TeleportHereCommand {

    private final HeriaBukkit bukkit;

    public TeleportHereCommand(HeriaBukkit bukkit) {
        this.bukkit = bukkit;
    }

    @HeriaCommand(name = "tphere", power = HeriaRank.MOD, inGameOnly = true, aliases = {"tph"})
    public void onTeleportHereCommand(CommandArgs args){
        Player player = args.getPlayer();

        if(args.getArgs().length != 1){
            player.sendMessage("§c/tphere <joueur>");
            return;
        }

        String name = args.getArgs(0);
        Player target = Bukkit.getPlayer(name);

        if(target == null){
            player.sendMessage("§cCe joueur n'est pas connecté sur votre serveur");
            return;
        }

        target.teleport(player.getLocation());
        player.sendMessage("§e[§6§lMOD§e] §b" + target.getName() + " §fa été téléporté sur §bvous§f.");
    }
}
