package fr.heriamc.bukkit.chat;

import fr.heriamc.api.data.SerializableData;

import java.util.UUID;

public class HeriaChatMessage implements SerializableData<UUID> {

    private UUID uuid;
    private UUID sender;
    private String content;
    private boolean reported;

    public HeriaChatMessage(UUID uuid, UUID sender, String content, boolean reported) {
        this.uuid = uuid;
        this.sender = sender;
        this.content = content;
        this.reported = reported;
    }

    public UUID getUuid() {
        return uuid;
    }

    public HeriaChatMessage setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public UUID getSender() {
        return sender;
    }

    public HeriaChatMessage setSender(UUID sender) {
        this.sender = sender;
        return this;
    }

    public String getContent() {
        return content;
    }

    public HeriaChatMessage setContent(String content) {
        this.content = content;
        return this;
    }

    public boolean isReported() {
        return reported;
    }

    public HeriaChatMessage setReported(boolean reported) {
        this.reported = reported;
        return this;
    }

    @Override
    public UUID getIdentifier() {
        return this.uuid;
    }

    @Override
    public void setIdentifier(UUID identifier) {
        this.uuid = identifier;
    }
}
