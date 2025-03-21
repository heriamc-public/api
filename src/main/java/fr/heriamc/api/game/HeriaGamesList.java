package fr.heriamc.api.game;

import fr.heriamc.api.data.SerializableData;
import fr.heriamc.api.server.HeriaServerType;

import java.util.List;

public class HeriaGamesList implements SerializableData<String>{

    private HeriaServerType serverType;
    private String serverName;
    private List<HeriaGameInfo> games;

    public HeriaGamesList(HeriaServerType serverType, String serverName, List<HeriaGameInfo> games) {
        this.serverType = serverType;
        this.serverName = serverName;
        this.games = games;
    }

    public HeriaServerType getServerType() {
        return serverType;
    }

    public HeriaGamesList setServerType(HeriaServerType serverType) {
        this.serverType = serverType;
        return this;
    }

    public String getServerName() {
        return serverName;
    }

    public HeriaGamesList setServerName(String serverName) {
        this.serverName = serverName;
        return this;
    }

    public List<HeriaGameInfo> getGames() {
        return games;
    }

    public HeriaGamesList setGames(List<HeriaGameInfo> games) {
        this.games = games;
        return this;
    }

    @Override
    public String getIdentifier() {
        return this.serverName;
    }

    @Override
    public void setIdentifier(String identifier) {
        // nothing
    }

}
