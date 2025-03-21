package fr.heriamc.bukkit.prefix;


import fr.heriamc.api.data.SerializableData;

import java.util.Date;
import java.util.UUID;

public class PrefixRequest implements SerializableData<UUID> {

    private UUID id;
    private UUID player;
    private String tag;
    private Date instant;

    public PrefixRequest(UUID id, UUID player, String tag, Date instant) {
        this.id = id;
        this.player = player;
        this.tag = tag;
        this.instant = instant;
    }

    public UUID getId() {
        return id;
    }

    public PrefixRequest setId(UUID id) {
        this.id = id;
        return this;
    }

    public UUID getPlayer() {
        return player;
    }

    public PrefixRequest setPlayer(UUID player) {
        this.player = player;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public PrefixRequest setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public Date getInstant() {
        return instant;
    }

    public PrefixRequest setInstant(Date instant) {
        this.instant = instant;
        return this;
    }

    @Override
    public UUID getIdentifier() {
        return id;
    }

    @Override
    public void setIdentifier(UUID identifier) {
        this.id = identifier;
    }
}
