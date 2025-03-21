package fr.heriamc.api.sanction;

import com.mongodb.client.model.Filters;
import fr.heriamc.api.data.PersistentDataManager;
import fr.heriamc.api.data.mongo.MongoConnection;
import fr.heriamc.api.data.redis.RedisConnection;
import fr.heriamc.api.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class HeriaSanctionManager extends PersistentDataManager<UUID, HeriaSanction> {

    public HeriaSanctionManager(RedisConnection redisConnection, MongoConnection mongoConnection) {
        super(redisConnection, mongoConnection, "sanctions", "sanctions");
    }

    @Override
    public HeriaSanction getDefault() {
        return new HeriaSanction(null, null, HeriaSanctionType.KICK, new Date(), null, "default reason", 0, false);
    }

    public List<HeriaSanction> getAllSanctions(UUID uuid){
        return this.mongoConnection.getDatabase().getCollection(this.mongoCollection)
                .find(Filters.eq("player", uuid.toString()))
                .map(document -> get(UUID.fromString(String.valueOf(document.get("id")))))
                .into(new ArrayList<>());
    }

    public List<HeriaSanction> getActiveSanctions(UUID uuid, HeriaSanctionType type) {
        return this.getAllSanctions(uuid).stream()
                .filter(sanction -> sanction.getType() == type)
                .filter(sanction -> !sanction.isExpired())
                .filter(sanction -> !sanction.isRemoved())
                .toList();
    }

    public String getBanMessage(HeriaSanction sanction){
        long expireTime = sanction.getWhen().toInstant().toEpochMilli() + sanction.getDuration() * 1000L;
        return "§r§6§lHERIAMC\n" +
                "§r \n" +
                "§r§cVous avez été exclu du serveur:\n" +
                "§r§cVous êtes banni du serveur\n" +
                "§r \n" +
                "§r§cDurée: " + TimeUtils.transformLongToFormatedDate(expireTime) + "\n" +
                "§r§cRaison: " + sanction.getReason() + "\n" +
                "§r \n" +
                "§r§7ID: §f" + sanction.getIdentifier().toString().split("-")[0] + "\n" +
                "§r§7Partager cet ID peut affecter votre bannissement";
    }

    public String getKickMessage(HeriaSanction sanction){
        long expireTime = sanction.getWhen().toInstant().toEpochMilli() + sanction.getDuration() * 1000L;
        return "§r§6§lHERIAMC\n" +
                "§r \n" +
                "§r§cVous avez été exclu du serveur:\n" +
                "§r§c" + sanction.getReason() + "\n" +
                "§r \n" +
                "§r§7ID: §f" + sanction.getIdentifier().toString().split("-")[0] + "\n" +
                "§r§7Partager cet ID peut affecter votre bannissement";
    }


    public List<String> getMuteMessage(HeriaSanction sanction){
        long expireTime = sanction.getWhen().toInstant().toEpochMilli() + sanction.getDuration() * 1000L;

        List<String> messages = new ArrayList<>();

        messages.add(" ");
        messages.add("§cVous êtes mute pour " + sanction.getReason() + " !");
        messages.add("§cDurée: " + TimeUtils.transformLongToFormatedDate(expireTime));
        messages.add(" ");

        return messages;
    }
}
