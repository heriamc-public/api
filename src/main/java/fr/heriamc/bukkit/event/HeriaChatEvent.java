package fr.heriamc.bukkit.event;

import fr.heriamc.api.user.HeriaPlayer;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class HeriaChatEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final HeriaPlayer heriaPlayer;

    private final String name;
    private final String formattedMessage;
    private final String displayedRank;

    private final TextComponent reportComponent;
    private final TextComponent messageComponent;

    private boolean cancelled;

    public HeriaChatEvent(Player player, HeriaPlayer heriaPlayer, String name, String formattedMessage, String displayedRank, TextComponent reportComponent, TextComponent messageComponent) {
        this.player = player;
        this.heriaPlayer = heriaPlayer;
        this.name = name;
        this.formattedMessage = formattedMessage;
        this.displayedRank = displayedRank;
        this.reportComponent = reportComponent;
        this.messageComponent = messageComponent;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    public Player getPlayer() {
        return player;
    }

    public HeriaPlayer getHeriaPlayer() {
        return heriaPlayer;
    }

    public String getName() {
        return name;
    }

    public String getDisplayedRank() {
        return displayedRank;
    }

    public String getFormattedMessage() {
        return formattedMessage;
    }

    public TextComponent getReportComponent() {
        return reportComponent;
    }

    public TextComponent getMessageComponent() {
        return messageComponent;
    }
}
