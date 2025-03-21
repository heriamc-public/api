package fr.heriamc.bukkit.instance.command;

import fr.heriamc.api.server.HeriaServerType;
import fr.heriamc.api.user.HeriaPlayer;
import fr.heriamc.api.user.rank.HeriaRank;
import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.command.CommandArgs;
import fr.heriamc.bukkit.command.HeriaCommand;
import fr.heriamc.proxy.queue.packet.QueueJoinPacket;
import org.bukkit.entity.Player;

public class HubCommand {

    private final HeriaBukkit bukkit;

    public HubCommand(HeriaBukkit bukkit) {
        this.bukkit = bukkit;
    }

    @HeriaCommand(name = "hub", power = HeriaRank.PLAYER, description = "Vous téléporte au hub")
    public void onCommand(CommandArgs args){
        Player player = args.getPlayer();
        HeriaPlayer heriaPlayer = bukkit.getApi().getPlayerManager().get(player.getUniqueId());

        if(heriaPlayer.getConnectedTo().startsWith("hub")){
            player.sendMessage("§cVous êtes déjà connecté à un hub ! Utilisez le dernier item dans votre inventaire pour changer de hub.");
            return;
        }
        if(heriaPlayer.isInQueue()){
            player.sendMessage("§cVous êtes déjà dans une file d'attente pour un serveur.");
            return;
        }

        bukkit.getApi().getMessaging().send(new QueueJoinPacket(player.getUniqueId(), null, null, HeriaServerType.HUB, null));
    }
}
