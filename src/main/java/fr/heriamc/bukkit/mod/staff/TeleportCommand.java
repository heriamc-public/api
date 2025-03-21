package fr.heriamc.bukkit.mod.staff;

import fr.heriamc.api.user.rank.HeriaRank;
import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.command.CommandArgs;
import fr.heriamc.bukkit.command.HeriaCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TeleportCommand {

    private final HeriaBukkit bukkit;

    public TeleportCommand(HeriaBukkit bukkit) {
        this.bukkit = bukkit;
    }

    @HeriaCommand(name = "tp", power = HeriaRank.MOD, inGameOnly = true, aliases = {"teleport"})
    public void onTeleportCommand(CommandArgs args){
        Player player = args.getPlayer();

        if(args.getArgs().length != 1){
            player.sendMessage("§c/tp <joueur>");
            return;
        }

        String name = args.getArgs(0);
        Player target = Bukkit.getPlayer(name);

        if(target == null){
            player.sendMessage("§cCe joueur n'est pas connecté sur votre serveur");
            return;
        }

        player.teleport(target.getLocation());
        player.sendMessage("§e[§6§lMOD§e] §fVous avez été téléporté sur §b" + target.getName() + "§f.");
    }
}
