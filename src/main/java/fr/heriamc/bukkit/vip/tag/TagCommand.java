package fr.heriamc.bukkit.vip.tag;

import fr.heriamc.api.user.HeriaPlayer;
import fr.heriamc.api.user.rank.HeriaRank;
import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.command.CommandArgs;
import fr.heriamc.bukkit.command.HeriaCommand;
import fr.heriamc.bukkit.event.TagEvent;
import org.bukkit.entity.Player;

public class TagCommand {

    private final HeriaBukkit bukkit;

    public TagCommand(HeriaBukkit heriaBukkit) {
        this.bukkit = heriaBukkit;
    }

    @HeriaCommand(name = "tag", power = HeriaRank.VIP, description = "Vous permet de désactiver votre grade")
    public void onTagCommand(CommandArgs args){
        Player player = args.getPlayer();

        HeriaPlayer heriaPlayer = bukkit.getApi().getPlayerManager().get(player.getUniqueId());

        boolean isTagRemoved = heriaPlayer.isRemovedTag();
        heriaPlayer.setRemovedTag(!isTagRemoved);

        String message = isTagRemoved ? "§aVous avez réactivé votre grade" : "§cVous avez désactivé votre grade";
        player.sendMessage(message);

        bukkit.getApi().getPlayerManager().save(heriaPlayer);
        bukkit.getServer().getPluginManager().callEvent(new TagEvent(player, !isTagRemoved));
    }
}
