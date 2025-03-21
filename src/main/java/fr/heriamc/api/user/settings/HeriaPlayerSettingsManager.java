package fr.heriamc.api.user.settings;

import fr.heriamc.api.data.PersistentDataManager;
import fr.heriamc.api.data.mongo.MongoConnection;
import fr.heriamc.api.data.redis.RedisConnection;
import fr.heriamc.api.user.settings.HeriaPlayerSettings.UserSettingState;

import java.util.UUID;

public class HeriaPlayerSettingsManager extends PersistentDataManager<UUID, HeriaPlayerSettings> {

    public HeriaPlayerSettingsManager(RedisConnection redisConnection, MongoConnection mongoConnection) {
        super(redisConnection, mongoConnection, "playerSettings", "playerSettings");
    }

    @Override
    public HeriaPlayerSettings getDefault() {
        return new HeriaPlayerSettings(null, UserSettingState.ALL, UserSettingState.ALL, UserSettingState.ALL , UserSettingState.ALL);
    }

}
