package fr.heriamc.bukkit.report;

import fr.heriamc.api.data.PersistentDataManager;
import fr.heriamc.api.data.mongo.MongoConnection;
import fr.heriamc.api.data.redis.RedisConnection;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class HeriaReportManager extends PersistentDataManager<UUID, HeriaReport> {

    public HeriaReportManager(RedisConnection redisConnection, MongoConnection mongoConnection) {
        super(redisConnection, mongoConnection, "reports", "reports");
    }

    @Override
    public HeriaReport getDefault() {
        return new HeriaReport(null, null, null, null, "", new Date());
    }

    public List<HeriaReport> getAllReports(HeriaReportType type){
        List<HeriaReport> persistent = this.getAllFromPersistent();
        persistent.removeIf(heriaReport -> heriaReport.getType() != type);

        return persistent;
    }
}
