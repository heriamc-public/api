package fr.heriamc.bukkit.announce.base;

import fr.heriamc.api.user.rank.HeriaRank;
import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.command.CommandArgs;
import fr.heriamc.bukkit.command.HeriaCommand;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class AnnounceCommand {

    private final HeriaBukkit bukkit;

    public AnnounceCommand(HeriaBukkit bukkit) {
        this.bukkit = bukkit;
    }

    @HeriaCommand(name = "annonce", power = HeriaRank.CUSTOM, description = "Vous permet de faire une annonce sur tout le serveur")
    public void onAnnounceCommand(CommandArgs args){
        Player player = args.getPlayer();

        if(args.getArgs().length < 1){
            player.sendMessage("§c/annonce <message>");
            return;
        }

        String message = String.join(" ", Arrays.copyOfRange(args.getArgs(), 0, args.getArgs().length));
        this.bukkit.getMenuManager().open(new AnnounceMenu(player, bukkit, message));
    }
}
