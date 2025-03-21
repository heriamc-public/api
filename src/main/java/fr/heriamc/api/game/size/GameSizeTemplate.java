package fr.heriamc.api.game.size;

import java.util.EnumSet;

public enum GameSizeTemplate {

    FFA("FFA", 0, 999, 0, 0),
    SIZE_SOLO("solo", 2, 8, 8, 1),
    SIZE_1V1("1vs1", 2, 2, 2, 1),
    SIZE_2V2("2vs2", 2, 4, 2, 2),
    SIZE_3V3("3vs3", 3, 6, 2, 3),
    SIZE_4V4("4vs4", 4, 8, 2, 4),
    SIZE_5V5("5vs5", 5, 10, 2, 5),
    SIZE_10V10("10vs10", 10, 20, 2, 10);

    private final String name;
    private final int minPlayer, maxPlayer, teamNeeded, teamMaxPlayer;

    public static final EnumSet<GameSizeTemplate> templates = EnumSet.allOf(GameSizeTemplate.class);

    GameSizeTemplate(String name, int minPlayer, int maxPlayer, int teamNeeded, int teamMaxPlayer) {
        this.name = name;
        this.minPlayer = minPlayer;
        this.maxPlayer = maxPlayer;
        this.teamNeeded = teamNeeded;
        this.teamMaxPlayer = teamMaxPlayer;
    }

    public GameSize toGameSize() {
        return new GameSize(this);
    }

    public String getName() {
        return name;
    }

    public int getMinPlayer() {
        return minPlayer;
    }

    public int getMaxPlayer() {
        return maxPlayer;
    }

    public int getTeamNeeded() {
        return teamNeeded;
    }

    public int getTeamMaxPlayer() {
        return teamMaxPlayer;
    }
}