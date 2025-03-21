package fr.heriamc.api.game;

import fr.heriamc.api.data.CacheDataManager;
import fr.heriamc.api.data.redis.RedisConnection;
import fr.heriamc.api.game.size.GameSize;
import fr.heriamc.api.server.HeriaServerType;

import java.util.ArrayList;
import java.util.List;

public class HeriaGameManager extends CacheDataManager<String, HeriaGamesList> {

    public HeriaGameManager(RedisConnection redisConnection) {
        super(redisConnection, "games");
    }

    public String getServerName(HeriaGameInfo gameInfo){
        return "";
    }

    public List<HeriaGameInfo> getGames(String serverName){
        return this.get(serverName).getGames();
    }

    public List<HeriaGameInfo> getGames(HeriaServerType serverType, GameSize gameSize){
        List<HeriaGameInfo> games = new ArrayList<>();
        for (HeriaGamesList game : this.getAllInCache()) {

            if(game.getServerType() != serverType){
                continue;
            }

            for (HeriaGameInfo gameInfo : game.getGames()) {
                if(gameInfo.getGameSize().equals(gameSize)){
                    games.add(gameInfo);
                }
            }

        }
        return games;
    }



}
