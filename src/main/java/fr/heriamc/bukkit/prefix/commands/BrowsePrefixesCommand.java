package fr.heriamc.bukkit.prefix.commands;

import fr.heriamc.api.user.HeriaPlayer;
import fr.heriamc.api.user.rank.HeriaRank;
import fr.heriamc.api.user.resolver.HeriaPlayerResolver;
import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.command.CommandArgs;
import fr.heriamc.bukkit.command.HeriaCommand;
import fr.heriamc.bukkit.prefix.menu.PrefixRequestsMenu;
import org.bukkit.entity.Player;

public class BrowsePrefixesCommand {

    private final HeriaBukkit bukkit;

    public BrowsePrefixesCommand(HeriaBukkit bukkit) {
        this.bukkit = bukkit;
    }

    @HeriaCommand(name = "browseprefixes", power = HeriaRank.SUPER_MOD)
    public void onBrowsePrefixesCommand(CommandArgs args){
        Player player = args.getPlayer();

        bukkit.getMenuManager().open(new PrefixRequestsMenu(player, bukkit));
    }

    @HeriaCommand(name = "removeprefix", power = HeriaRank.CUSTOM, description = "Vous permet de retirer votre préfixe")
    public void onRemovePrefixCommand(CommandArgs args){

        Player player = args.getPlayer();

        HeriaPlayer heriaPlayer = bukkit.getApi().getPlayerManager().get(player.getUniqueId());
        int length = args.getArgs().length;

        if (length == 0) {
            if (heriaPlayer.getCustomPrefix() == null) {
                player.sendMessage("§cVous n'avez pas de prefix.");
                return;
            }

            heriaPlayer.setCustomPrefix(null);
            bukkit.getApi().getPlayerManager().save(heriaPlayer);
            player.sendMessage("§aVous avez supprimer votre prefix.");
            return;
        }


        if (length == 1) {
            if (heriaPlayer.getRank().getPower() < HeriaRank.SUPER_MOD.getPower()) {
                player.sendMessage("§cVous n'avez pas la permission.");
                return;
            }

            String targetName = args.getArgs(0);
            HeriaPlayerResolver resolver = bukkit.getApi().getResolverManager().get(targetName);

            if (resolver == null) {
                player.sendMessage("§cCe joueur ne s'est jamais connecté.");
                return;
            }

            HeriaPlayer target = bukkit.getApi().getPlayerManager().get(resolver.getUuid());

            if(target == null) {
                player.sendMessage("§cCe joueur n'existe plus, il a probablement changé de pseudonyme.");
                return;
            }

            if (target.getCustomPrefix() == null) {
                player.sendMessage("§cCe joueur ne possède pas de prefix");
                return;
            }

            target.setCustomPrefix(null);
            bukkit.getApi().getPlayerManager().save(target);

            player.sendMessage("§aVous avez retirer le prefix de §e" + target.getName() + "§a.");
        }
    }

}