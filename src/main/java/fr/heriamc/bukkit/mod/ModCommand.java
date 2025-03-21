package fr.heriamc.bukkit.mod;

import fr.heriamc.api.user.HeriaPlayer;
import fr.heriamc.api.user.rank.HeriaRank;
import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.command.CommandArgs;
import fr.heriamc.bukkit.command.HeriaCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ModCommand {

    private final ModManager modManager;
    private final HeriaBukkit bukkit;

    public ModCommand(ModManager modManager, HeriaBukkit bukkit) {
        this.modManager = modManager;
        this.bukkit = bukkit;
    }

    @HeriaCommand(name = "mod", power = HeriaRank.MOD)
    public void onModCommand(CommandArgs args){
        Player player = args.getPlayer();
        HeriaPlayer heriaPlayer = bukkit.getApi().getPlayerManager().get(player.getUniqueId());

        if(!bukkit.getInstanceName().startsWith("hub")){
            player.sendMessage("§cVous ne pouvez pas enlever ou activer le mode modération dans une partie !");
            return;
        }

        if(!heriaPlayer.isMod()){
            modManager.setVanished(player, heriaPlayer, true);

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                player.showPlayer(onlinePlayer);
            }

            player.sendMessage("§dMode Modération et vanish : Activé.");
        } else {
            player.getInventory().clear();

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                HeriaPlayer heriaOther = bukkit.getApi().getPlayerManager().get(onlinePlayer.getUniqueId());
                if(heriaOther.isVanished()){
                    player.hidePlayer(onlinePlayer);
                }
            }

            modManager.setVanished(player, heriaPlayer, false);
            player.sendMessage("§cVous avez désactivé le mode modération et le vanish vous à été désactivé");

        }

        heriaPlayer.setMod(!heriaPlayer.isMod());
        bukkit.getApi().getPlayerManager().save(heriaPlayer);

        Bukkit.getServer().getPluginManager().callEvent(new ModEvent(player, heriaPlayer.isMod()));
    }

    @HeriaCommand(name = "cc.t", power = HeriaRank.MOD)
    public void onCCTCommand(CommandArgs args){
        Player player = args.getPlayer();
        HeriaPlayer heriaPlayer = bukkit.getApi().getPlayerManager().get(player.getUniqueId());

        if(!heriaPlayer.isMod()){
            player.sendMessage("§cVous devez être en mode modération pour utiliser cette commande.");
            return;
        }

        //if(heriaPlayer.)

        modManager.giveItems(player);
    }
}
