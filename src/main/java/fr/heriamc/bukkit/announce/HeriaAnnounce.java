package fr.heriamc.bukkit.announce;

import fr.heriamc.api.data.SerializableData;

import java.util.UUID;

public class HeriaAnnounce implements SerializableData<UUID> {

    private UUID id;
    private long instant;
    private AnnounceTime duration;
    private String message;
    private UUID user;

    public HeriaAnnounce(UUID id, long instant, AnnounceTime duration, String message, UUID user) {
        this.id = id;
        this.instant = instant;
        this.duration = duration;
        this.message = message;
        this.user = user;
    }

    public UUID getId() {
        return id;
    }

    public HeriaAnnounce setId(UUID id) {
        this.id = id;
        return this;
    }

    public long getInstant() {
        return instant;
    }

    public HeriaAnnounce setInstant(long instant) {
        this.instant = instant;
        return this;
    }

    public AnnounceTime getDuration() {
        return duration;
    }

    public HeriaAnnounce setDuration(AnnounceTime duration) {
        this.duration = duration;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public HeriaAnnounce setMessage(String message) {
        this.message = message;
        return this;
    }

    public UUID getUser() {
        return user;
    }

    public HeriaAnnounce setUser(UUID user) {
        this.user = user;
        return this;
    }

    @Override
    public UUID getIdentifier() {
        return this.id;
    }

    @Override
    public void setIdentifier(UUID identifier) {
        this.id = identifier;
    }
}
