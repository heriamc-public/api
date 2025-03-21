package fr.heriamc.bukkit.instance;

import fr.heriamc.api.server.HeriaServer;
import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.utils.Title;
import fr.heriamc.proxy.packet.model.SendPlayerPacket;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class InstanceConnectRunnable extends BukkitRunnable {

    private final HeriaBukkit bukkit;
    private final Player player;
    private final String serverName;

    private String loading = "▂▃▄▅▆▇▉▇▆▅▄▃";

    public InstanceConnectRunnable(HeriaBukkit bukkit, Player player, String serverName) {
        this.bukkit = bukkit;
        this.player = player;
        this.serverName = serverName;
    }

    @Override
    public void run() {
        HeriaServer server = this.bukkit.getApi().getServerManager().get(this.serverName);

        if (server.getStatus().isReachable()) {
            Title.sendTitle(this.player, 0, 20, 0, "", "§aTéléportation vers votre serveur...");
            this.cancel();
            this.player.playSound(this.player.getLocation(), Sound.NOTE_PLING, 5.0F, 1.0F);
            this.bukkit.getApi().getMessaging().send(new SendPlayerPacket(player.getUniqueId(), this.serverName));
            return;

        }

        Title.sendTitle(this.player, 0, 6, 0, this.loading, "§7Création de votre serveur...");
        this.loading = this.moveLastCharToFront(this.loading);
    }

    private String moveLastCharToFront(String input) {
        if (input != null && !input.isEmpty()) {
            char lastChar = input.charAt(input.length() - 1);
            String firstPart = input.substring(0, input.length() - 1);
            return lastChar + firstPart;
        } else {
            return input;
        }
    }
}
