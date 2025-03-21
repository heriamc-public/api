package fr.heriamc.api.user.resolver;

import fr.heriamc.api.data.SerializableData;

import java.util.UUID;

public class HeriaPlayerResolver implements SerializableData<String> {

    private String id;
    private UUID uuid;

    public HeriaPlayerResolver(String name, UUID uuid) {
        this.id = name;
        this.uuid = uuid;
    }

    @Override
    public String getIdentifier() {
        return id;
    }

    @Override
    public void setIdentifier(String identifier) {
        this.id = identifier;
    }

    public String getName() {
        return id;
    }

    public HeriaPlayerResolver setName(String id) {
        this.id = id;
        return this;
    }

    public UUID getUuid() {
        return uuid;
    }

    public HeriaPlayerResolver setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }
}
