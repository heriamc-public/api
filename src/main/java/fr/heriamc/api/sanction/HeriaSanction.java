package fr.heriamc.api.sanction;

import fr.heriamc.api.data.SerializableData;

import java.util.Date;
import java.util.UUID;

public class HeriaSanction implements SerializableData<UUID> {

    private UUID id;
    private UUID player;
    private HeriaSanctionType type;
    private Date when;
    private UUID by;
    private String reason;
    private long duration;
    private boolean removed;

    public HeriaSanction(UUID id, UUID player, HeriaSanctionType type, Date when, UUID by, String reason, long duration, boolean removed) {
        this.id = id;
        this.player = player;
        this.type = type;
        this.when = when;
        this.by = by;
        this.reason = reason;
        this.duration = duration;
        this.removed = removed;
    }

    public UUID getId() {
        return id;
    }

    public HeriaSanction setId(UUID id) {
        this.id = id;
        return this;
    }

    public UUID getPlayer() {
        return player;
    }

    public HeriaSanction setPlayer(UUID player) {
        this.player = player;
        return this;
    }

    public HeriaSanctionType getType() {
        return type;
    }

    public HeriaSanction setType(HeriaSanctionType type) {
        this.type = type;
        return this;
    }

    public Date getWhen() {
        return when;
    }

    public HeriaSanction setWhen(Date when) {
        this.when = when;
        return this;
    }

    public UUID getBy() {
        return by;
    }

    public HeriaSanction setBy(UUID by) {
        this.by = by;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public HeriaSanction setReason(String reason) {
        this.reason = reason;
        return this;
    }

    public long getDuration() {
        return duration;
    }

    public HeriaSanction setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    public boolean isRemoved() {
        return removed;
    }

    public HeriaSanction setRemoved(boolean removed) {
        this.removed = removed;
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

    public Date getExpiration() {
        long dur = getDuration();
        if (dur <= 0L)
            return null;
        return Date.from(getWhen().toInstant().plusSeconds(dur));
    }

    public boolean isExpired() {
        Date expiration = getExpiration();
        System.out.println("is expired = " + (expiration != null && expiration.before(new Date())));
        return (expiration != null && expiration.before(new Date()));
    }
}
