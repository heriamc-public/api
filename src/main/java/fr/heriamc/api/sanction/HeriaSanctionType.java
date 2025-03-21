package fr.heriamc.api.sanction;

public enum HeriaSanctionType {

    KICK("Kick"),
    MUTE("Mute"),
    BAN("Ban"),
    WARN("Warn");

    private final String name;

    HeriaSanctionType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
