package fr.heriamc.api.server;

import fr.heriamc.api.utils.HeriaChatColor;
import fr.heriamc.api.utils.HeriaSkull;

public enum HeriaServerStatus {

    STARTING("Démarrage", HeriaChatColor.GOLD, HeriaSkull.ORANGE, false),
    STARTED("Démarré", HeriaChatColor.GREEN, HeriaSkull.LIME, true),
    STOPPING("Arrêt", HeriaChatColor.RED, HeriaSkull.RED, false);

    private final String name;
    private final HeriaChatColor color;
    private final HeriaSkull skull;
    private final boolean reachable;
    
    HeriaServerStatus(String name, HeriaChatColor color, HeriaSkull skull, boolean reachable) {
        this.name = name;
        this.color = color;
        this.skull = skull;
        this.reachable = reachable;
    }

    public String getName() {
        return name;
    }

    public HeriaChatColor getColor() {
        return color;
    }

    public HeriaSkull getSkull() {
        return skull;
    }

    public boolean isReachable() {
        return reachable;
    }

}
