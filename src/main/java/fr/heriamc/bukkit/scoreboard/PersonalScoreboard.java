package fr.heriamc.bukkit.scoreboard;

import fr.heriamc.bukkit.utils.ObjectiveSign;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public abstract class PersonalScoreboard {

    protected final Player player;
    protected final UUID uuid;
    protected final ObjectiveSign objectiveSign;

    public PersonalScoreboard(Player player) {
        this.player = player;

        uuid = player.getUniqueId();
        objectiveSign = new ObjectiveSign("sidebar", "heriamc");
        objectiveSign.addReceiver(player);
    }

    public abstract void reloadData();

    public abstract void setLines(String ip);

    public void onLogout() {
        objectiveSign.removeReceiver(Bukkit.getServer().getOfflinePlayer(uuid));
    }
}
