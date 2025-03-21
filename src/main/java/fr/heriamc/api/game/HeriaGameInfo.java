package fr.heriamc.api.game;

import fr.heriamc.api.game.size.GameSize;

import java.util.List;
import java.util.UUID;

public class HeriaGameInfo {

    private String gameName;
    private String serverName;
    private List<UUID> players;
    private int playersCount, alivePlayersCount, spectatorsCount;
    private GameState state;
    private GameSize gameSize;

    public HeriaGameInfo(String gameName, String serverName, List<UUID> players, int playersCount, int alivePlayersCount, int spectatorsCount, GameState state, GameSize gameSize) {
        this.gameName = gameName;
        this.serverName = serverName;
        this.players = players;
        this.playersCount = playersCount;
        this.alivePlayersCount = alivePlayersCount;
        this.spectatorsCount = spectatorsCount;
        this.state = state;
        this.gameSize = gameSize;
    }

    public String getGameName() {
        return gameName;
    }

    public HeriaGameInfo setGameName(String gameName) {
        this.gameName = gameName;
        return this;
    }

    public String getServerName() {
        return serverName;
    }

    public HeriaGameInfo setServerName(String serverName) {
        this.serverName = serverName;
        return this;
    }

    public List<UUID> getPlayers() {
        return players;
    }

    public HeriaGameInfo setPlayers(List<UUID> players) {
        this.players = players;
        return this;
    }

    public int getPlayersCount() {
        return playersCount;
    }

    public HeriaGameInfo setPlayersCount(int playersCount) {
        this.playersCount = playersCount;
        return this;
    }

    public int getAlivePlayersCount() {
        return alivePlayersCount;
    }

    public HeriaGameInfo setAlivePlayersCount(int alivePlayersCount) {
        this.alivePlayersCount = alivePlayersCount;
        return this;
    }

    public int getSpectatorsCount() {
        return spectatorsCount;
    }

    public HeriaGameInfo setSpectatorsCount(int spectatorsCount) {
        this.spectatorsCount = spectatorsCount;
        return this;
    }

    public GameState getState() {
        return state;
    }

    public HeriaGameInfo setState(GameState state) {
        this.state = state;
        return this;
    }

    public GameSize getGameSize() {
        return gameSize;
    }

    public HeriaGameInfo setGameSize(GameSize gameSize) {
        this.gameSize = gameSize;
        return this;
    }
}
