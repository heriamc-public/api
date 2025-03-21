package fr.heriamc.bukkit.mod.freeze;

import fr.heriamc.api.user.rank.HeriaRank;
import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.command.CommandArgs;
import fr.heriamc.bukkit.command.HeriaCommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Map;

public class FreezeCommand {

    private final HeriaBukkit heriaBukkit;

    private final Map<Player, Location> freezedPlayers;
    private final FreezeTask freezeTask;

    public FreezeCommand(HeriaBukkit heriaBukkit) {
        this.heriaBukkit = heriaBukkit;
        this.freezeTask = new FreezeTask(heriaBukkit);
        this.freezedPlayers = freezeTask.getFreezedPlayers();
    }

    @HeriaCommand(name = "freeze", power = HeriaRank.MOD)
    public void execute(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();

        int length = commandArgs.getArgs().length;

        if (length == 0) {
            player.sendMessage("§c/freeze <joueur>");
            return;
        }

        if (length == 1) {
            String targetName = commandArgs.getArgs(0);
            Player target = Bukkit.getPlayer(targetName);

            if (target == null || !target.isOnline()) {
                player.sendMessage("§cCe joueur ne n'est pas connecté.");
                return;
            }

            if (!freezedPlayers.containsKey(target)) {
                freezedPlayers.putIfAbsent(target, target.getLocation());
                player.sendMessage("§aVous avez freeze §e" + target.getName() + " §aavec succèes");
            } else {
                player.sendMessage("§aVous avez unfreeze §e" + target.getName() + " §aavec succèes");
                freezedPlayers.remove(target);
            }
        }

    }

}