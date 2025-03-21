package fr.heriamc.bukkit.mod;

import fr.heriamc.api.user.HeriaPlayer;
import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.mod.freeze.FreezeCommand;
import fr.heriamc.bukkit.mod.history.HistoryCommand;
import fr.heriamc.bukkit.mod.rank.RankCommand;
import fr.heriamc.bukkit.mod.sanction.SanctionCommand;
import fr.heriamc.bukkit.mod.sanction.types.BanCommand;
import fr.heriamc.bukkit.mod.sanction.types.KickCommand;
import fr.heriamc.bukkit.mod.sanction.types.MuteCommand;
import fr.heriamc.bukkit.mod.sanction.types.WarnCommand;
import fr.heriamc.bukkit.mod.staff.*;
import fr.heriamc.bukkit.report.command.user.ReportPlayerCommand;
import fr.heriamc.bukkit.report.command.chat.ReportChatListCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public record ModManager(HeriaBukkit bukkit) {

    public ModManager(HeriaBukkit bukkit) {
        this.bukkit = bukkit;

        bukkit.getCommandManager().registerCommand(new RankCommand(bukkit));
        bukkit.getCommandManager().registerCommand(new ReportChatListCommand(bukkit));
        bukkit.getCommandManager().registerCommand(new ReportPlayerCommand(bukkit));
        bukkit.getCommandManager().registerCommand(new SanctionCommand(bukkit));
        bukkit.getCommandManager().registerCommand(new StaffCommand(bukkit));
        bukkit.getCommandManager().registerCommand(new TeleportHereCommand(bukkit));
        bukkit.getCommandManager().registerCommand(new TeleportCommand(bukkit));
        bukkit.getCommandManager().registerCommand(new HistoryCommand(bukkit));
        bukkit.getCommandManager().registerCommand(new CheckCommand(bukkit));
        bukkit.getCommandManager().registerCommand(new ChatStaffCommand(bukkit));
        bukkit.getCommandManager().registerCommand(new FreezeCommand(bukkit));

        bukkit.getCommandManager().registerCommand(new BanCommand(bukkit));
        bukkit.getCommandManager().registerCommand(new KickCommand(bukkit));
        bukkit.getCommandManager().registerCommand(new MuteCommand(bukkit));
        bukkit.getCommandManager().registerCommand(new WarnCommand(bukkit));

        bukkit.getCommandManager().registerCommand(new ModCommand(this, bukkit));
        bukkit.getServer().getPluginManager().registerEvents(new ModListener(this, bukkit), bukkit);
        bukkit.getServer().getScheduler().runTaskTimer(bukkit, new ModTask(bukkit), 0L, 20L);
    }

    public void setVanished(Player player, HeriaPlayer heriaPlayer, boolean state) {

        heriaPlayer.setVanished(state);
        bukkit.getApi().getPlayerManager().save(heriaPlayer);

        if (state) {
            player.setAllowFlight(true);
            player.setFlying(true);

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                HeriaPlayer heriaOnline = bukkit.getApi().getPlayerManager().get(onlinePlayer.getUniqueId());

                if(heriaOnline.isMod()){
                    continue;
                }

                onlinePlayer.hidePlayer(player);
            }

        } else {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.showPlayer(player);
            }
        }
    }

    public void giveItems(Player player){
        for (ModItem value : ModItem.values()) {
            value.setItem(player.getInventory());
        }
    }
}
