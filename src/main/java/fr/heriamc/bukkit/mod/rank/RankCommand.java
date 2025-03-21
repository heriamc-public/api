package fr.heriamc.bukkit.mod.rank;

import fr.heriamc.api.user.HeriaPlayer;
import fr.heriamc.api.user.rank.HeriaRank;
import fr.heriamc.api.user.resolver.HeriaPlayerResolver;
import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.command.CommandArgs;
import fr.heriamc.bukkit.command.HeriaCommand;
import org.bukkit.entity.Player;

public class RankCommand {

    private final HeriaBukkit bukkit;

    public RankCommand(HeriaBukkit heriaBukkit) {
        this.bukkit = heriaBukkit;
    }

    @HeriaCommand(name = "rank", inGameOnly = true, power = HeriaRank.RESP_PERM)
    public void onRankCommand(CommandArgs args){
        Player player = args.getPlayer();

        if(args.getArgs().length != 1){
            player.sendMessage("§c/rank <joueur>");
            return;
        }

        String targetName = args.getArgs(0);
        HeriaPlayerResolver resolver = bukkit.getApi().getResolverManager().get(targetName);

        if(resolver == null){
            player.sendMessage("§cCe joueur n'existe pas");
            return;
        }

        HeriaPlayer target = bukkit.getApi().getPlayerManager().get(resolver.getUuid());

        if(target == null){
            player.sendMessage("§cCe joueur n'existe plus, il a surement changé de pseudo");
            return;
        }

        bukkit.getMenuManager().open(new RankMenu(player, bukkit, target));


    }
}
