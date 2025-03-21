package fr.heriamc.bukkit.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TagEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final boolean activated;

    public TagEvent(Player player, boolean activated) {
        this.player = player;
        this.activated = activated;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isActivated() {
        return activated;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
