package fr.heriamc.api.game;

import fr.heriamc.api.utils.HeriaChatColor;
import fr.heriamc.api.utils.HeriaSkull;

public enum GameState {

    LOADING(HeriaChatColor.RED, HeriaSkull.RED),
    LOADING_IN_PROGRESS(HeriaChatColor.RED, HeriaSkull.RED),

    WAIT(HeriaChatColor.GREEN, HeriaSkull.LIME),
    ALWAYS_PLAYING(HeriaChatColor.GREEN, HeriaSkull.LIME),
    STARTING(HeriaChatColor.YELLOW, HeriaSkull.YELLOW),
    IN_GAME(HeriaChatColor.GOLD, HeriaSkull.ORANGE),
    END(HeriaChatColor.RED, HeriaSkull.RED);

    private final HeriaChatColor color;
    private final HeriaSkull skull;

    GameState(HeriaChatColor color, HeriaSkull skull) {
        this.color = color;
        this.skull = skull;
    }

    public HeriaChatColor getColor() {
        return color;
    }

    public HeriaSkull getSkull() {
        return skull;
    }

    public boolean is(GameState state) {
        return this == state;
    }

    public boolean is(GameState... states) {
        for (GameState state : states)
            if (this == state) return true;

        return false;
    }

}