package fr.heriamc.bukkit.mod.history;

import fr.heriamc.api.user.HeriaPlayer;
import fr.heriamc.api.user.rank.HeriaRank;
import fr.heriamc.api.user.resolver.HeriaPlayerResolver;
import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.command.CommandArgs;
import fr.heriamc.bukkit.command.HeriaCommand;
import org.bukkit.entity.Player;

public class HistoryCommand {

    private final HeriaBukkit bukkit;

    public HistoryCommand(HeriaBukkit bukkit) {
        this.bukkit = bukkit;
    }

    @HeriaCommand(name = "history", inGameOnly = true, power = HeriaRank.SUPER_MOD)
    public void onHistoryCommand(CommandArgs args){
        Player player = args.getPlayer();

        if(args.getArgs().length != 1){
            player.sendMessage("§c/history <joueur>");
            return;
        }

        String targetName = args.getArgs(0);
        HeriaPlayerResolver resolver = bukkit.getApi().getResolverManager().get(targetName);

        if(resolver == null){
            player.sendMessage("§cCe joueur n'existe pas");
            return;
        }


        HeriaPlayer heriaTarget = bukkit.getApi().getPlayerManager().get(resolver.getUuid());

        if(heriaTarget == null){
            player.sendMessage("§cCe joueur n'existe plus");
            return;
        }

        bukkit.getMenuManager().open(new HistoryMenu(player, bukkit, heriaTarget));
    }
}
