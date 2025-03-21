package fr.heriamc.api.game.size;

import java.util.Objects;

public class GameSize {

    private String name;
    private int minPlayer, maxPlayer, teamNeeded, teamMaxPlayer;

    public GameSize(GameSizeTemplate template) {
        this.name = template.getName();
        this.minPlayer = template.getMinPlayer();
        this.maxPlayer = template.getMaxPlayer();
        this.teamNeeded = template.getTeamNeeded();
        this.teamMaxPlayer = template.getTeamMaxPlayer();
    }

    public GameSize(String name, int minPlayer, int maxPlayer, int teamNeeded, int teamMaxPlayer) {
        this.name = name;
        this.minPlayer = minPlayer;
        this.maxPlayer = maxPlayer;
        this.teamNeeded = teamNeeded;
        this.teamMaxPlayer = teamMaxPlayer;
    }

    public String getName() {
        return name;
    }

    public GameSize setName(String name) {
        this.name = name;
        return this;
    }

    public int getMinPlayer() {
        return minPlayer;
    }

    public GameSize setMinPlayer(int minPlayer) {
        this.minPlayer = minPlayer;
        return this;
    }

    public int getMaxPlayer() {
        return maxPlayer;
    }

    public GameSize setMaxPlayer(int maxPlayer) {
        this.maxPlayer = maxPlayer;
        return this;
    }

    public int getTeamNeeded() {
        return teamNeeded;
    }

    public GameSize setTeamNeeded(int teamNeeded) {
        this.teamNeeded = teamNeeded;
        return this;
    }

    public int getTeamMaxPlayer() {
        return teamMaxPlayer;
    }

    public GameSize setTeamMaxPlayer(int teamMaxPlayer) {
        this.teamMaxPlayer = teamMaxPlayer;
        return this;
    }

    public int calculateMapCapacity() {
        return maxPlayer == 999 ? 30 : maxPlayer;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        GameSize other = (GameSize) obj;
        return minPlayer == other.minPlayer &&
                maxPlayer == other.maxPlayer &&
                teamNeeded == other.teamNeeded &&
                teamMaxPlayer == other.teamMaxPlayer &&
                (Objects.equals(name, other.name));
    }
}