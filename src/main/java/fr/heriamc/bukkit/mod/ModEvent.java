package fr.heriamc.bukkit.mod;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ModEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final boolean state;

    public ModEvent(Player player, boolean state) {
        this.player = player;
        this.state = state;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isState() {
        return state;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
