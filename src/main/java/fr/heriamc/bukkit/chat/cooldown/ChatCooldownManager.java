package fr.heriamc.bukkit.chat.cooldown;

import fr.heriamc.api.data.LocalDataExpiration;
import fr.heriamc.api.data.LocalDataManager;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class ChatCooldownManager extends LocalDataManager<Player, Boolean> {

    public ChatCooldownManager() {
        super(new LocalDataExpiration(2, TimeUnit.SECONDS));
    }
}
