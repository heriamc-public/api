package fr.heriamc.bukkit.instance;

import fr.heriamc.api.server.HeriaServerType;
import fr.heriamc.api.user.rank.HeriaRank;
import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.command.CommandArgs;
import fr.heriamc.bukkit.command.HeriaCommand;
import fr.heriamc.bukkit.instance.menu.InstanceListMenu;
import fr.heriamc.proxy.packet.model.ServerUnregisterPacket;
import fr.heriamc.proxy.queue.packet.InstanceStopPacket;
import fr.heriamc.proxy.queue.packet.QueueJoinPacket;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class InstanceCommand {

    private final HeriaBukkit bukkit;

    public InstanceCommand(HeriaBukkit heriaBukkit) {
        this.bukkit = heriaBukkit;
    }

    @HeriaCommand(name = "instances", power = HeriaRank.ADMIN, inGameOnly = true)
    public void onInstancesCommand(CommandArgs args){
        Player player = args.getPlayer();
        this.bukkit.getMenuManager().open(new InstanceListMenu(player, bukkit));
    }

    @HeriaCommand(name = "instances.stop", power = HeriaRank.ADMIN, inGameOnly = true)
    public void onInstancesStopCommand(CommandArgs args){
        Player player = args.getPlayer();

        if(bukkit.getInstanceName().startsWith("hub")){
            player.sendMessage("§cCette commande n'est pas appliquable sur un lobby");
            return;
        }

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            bukkit.getApi().getMessaging().send(new QueueJoinPacket(onlinePlayer.getUniqueId(), null, null, HeriaServerType.HUB, null));
        }

        bukkit.getServer().getScheduler().runTaskLater(bukkit, () -> {
            this.bukkit.getApi().getMessaging().send(new InstanceStopPacket(bukkit.getInstanceName()));
            this.bukkit.getApi().getMessaging().send(new ServerUnregisterPacket(bukkit.getInstanceName()));

            bukkit.getApi().getHeriaGameManager().remove(bukkit.getInstanceName());
            bukkit.getApi().getServerManager().remove(bukkit.getInstanceName());
            bukkit.getApi().getServerCreator().deleteServer(bukkit.getInstanceName());
        }, 10L);

    }

}
