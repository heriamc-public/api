package fr.heriamc.bukkit.mod.sanction.types;

import fr.heriamc.api.sanction.HeriaSanction;
import fr.heriamc.api.sanction.HeriaSanctionType;
import fr.heriamc.api.user.HeriaPlayer;
import fr.heriamc.api.user.rank.HeriaRank;
import fr.heriamc.api.user.resolver.HeriaPlayerResolver;
import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.command.CommandArgs;
import fr.heriamc.bukkit.command.HeriaCommand;
import fr.heriamc.bukkit.packet.BukkitBroadcastMessagePacket;
import fr.heriamc.bukkit.packet.BukkitPlayerMessagePacket;
import fr.heriamc.bukkit.utils.TimeUnit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MuteCommand {

    private final HeriaBukkit bukkit;

    public MuteCommand(HeriaBukkit bukkit) {
        this.bukkit = bukkit;
    }

    @HeriaCommand(name = "mute", power = HeriaRank.HELPER)
    public void onMuteCommand(CommandArgs args){
        Player player = args.getPlayer();
        HeriaPlayer heriaPlayer = bukkit.getApi().getPlayerManager().get(player.getUniqueId());

        if(args.getArgs().length < 3){
            player.sendMessage("§c/mute <joueur> <temps> <raison>");
            return;
        }

        String targetName = args.getArgs(0);
        HeriaPlayerResolver resolver = bukkit.getApi().getResolverManager().get(targetName);

        if(resolver == null){
            player.sendMessage("§cCe joueur ne s'est jamais connecté.");
            return;
        }

        HeriaPlayer target = bukkit.getApi().getPlayerManager().get(resolver.getUuid());

        if(target == null){
            player.sendMessage("§cCe joueur n'existe plus, il a probablement changé de pseudonyme.");
            return;
        }

        if(target.getRank().getPower() > heriaPlayer.getRank().getPower()){
            player.sendMessage("§cVous ne pouvez pas mute quelqu'un en dessus de vous");
            return;
        }

        String time = args.getArgs(1);
        Long formattedTime = TimeUnit.parseTimeStringToSeconds(time);

        if(formattedTime == null){
            player.sendMessage("§cLe temps n'est pas correct. exemple: 30d50m40s");
            return;
        }

        String reason = String.join(" ", Arrays.copyOfRange(args.getArgs(), 2, args.getArgs().length));

        HeriaSanction sanction = this.bukkit.getApi().getSanctionManager().createOrLoad(UUID.randomUUID());
        sanction.setPlayer(target.getId());
        sanction.setType(HeriaSanctionType.MUTE);
        sanction.setBy(player.getUniqueId());
        sanction.setReason(reason);
        sanction.setDuration(formattedTime);

        bukkit.getApi().getSanctionManager().save(sanction);
        bukkit.getApi().getSanctionManager().saveInPersistant(sanction);

        List<String> muteMessage = bukkit.getApi().getSanctionManager().getMuteMessage(sanction);
        bukkit.getApi().getMessaging().send(new BukkitPlayerMessagePacket(target.getId(), String.join("\n", muteMessage)));

        String broadcast = "§a" + player.getName() + " §fa mute §a" + target.getName() + " §fpour \"§c" + reason + "§f\" durant §c" + time;
        bukkit.getApi().getMessaging().send(new BukkitBroadcastMessagePacket(broadcast, HeriaRank.HELPER.getPower()));
    }

    @HeriaCommand(name = "unmute", power = HeriaRank.HELPER)
    public void onUnMuteCommand(CommandArgs args){
        Player player = args.getPlayer();

        if(args.getArgs().length < 1){
            player.sendMessage("§c/unmute <joueur>");
            return;
        }

        String targetName = args.getArgs(0);
        HeriaPlayerResolver resolver = bukkit.getApi().getResolverManager().get(targetName);

        if(resolver == null){
            player.sendMessage("§cCe joueur ne s'est jamais connecté.");
            return;
        }

        HeriaPlayer target = bukkit.getApi().getPlayerManager().get(resolver.getUuid());

        if(target == null){
            player.sendMessage("§cCe joueur n'existe plus, il a probablement changé de pseudonyme.");
            return;
        }

        List<HeriaSanction> mutes = this.bukkit.getApi().getSanctionManager().getActiveSanctions(target.getId(), HeriaSanctionType.MUTE);

        if(mutes.isEmpty()){
            player.sendMessage("§cCe joueur n'est pas mute");
            return;
        }

        int count = mutes.size();

        for (HeriaSanction mute : mutes) {
            mute.setRemoved(true);
            bukkit.getApi().getSanctionManager().save(mute);
            bukkit.getApi().getSanctionManager().saveInPersistant(mute);
        }

        String broadcast = "§a" + player.getName() + " §fa dé-mute §a" + target.getName() + " §f(" + count + ")";
        bukkit.getApi().getMessaging().send(new BukkitBroadcastMessagePacket(broadcast, HeriaRank.HELPER.getPower()));
    }
}
