package fr.heriamc.api.friends;

import fr.heriamc.api.data.SerializableData;

import java.util.UUID;

public class HeriaFriendLink implements SerializableData<UUID> {

    private UUID id;
    private HeriaFriendLinkStatus status;

    private UUID sender;
    private UUID receiver;

    private long instant;

    public HeriaFriendLink(UUID id, HeriaFriendLinkStatus status, UUID sender, UUID receiver, long instant) {
        this.id = id;
        this.status = status;
        this.sender = sender;
        this.receiver = receiver;
        this.instant = instant;
    }

    public UUID getId() {
        return id;
    }

    public HeriaFriendLink setId(UUID id) {
        this.id = id;
        return this;
    }

    public HeriaFriendLinkStatus getStatus() {
        return status;
    }

    public HeriaFriendLink setStatus(HeriaFriendLinkStatus status) {
        this.status = status;
        return this;
    }

    public UUID getSender() {
        return sender;
    }

    public HeriaFriendLink setSender(UUID sender) {
        this.sender = sender;
        return this;
    }

    public UUID getReceiver() {
        return receiver;
    }

    public HeriaFriendLink setReceiver(UUID receiver) {
        this.receiver = receiver;
        return this;
    }

    public long getInstant() {
        return instant;
    }

    public HeriaFriendLink setInstant(long instant) {
        this.instant = instant;
        return this;
    }

    public boolean hasLink(UUID left, UUID right) {
        return (this.getSender().equals(left) && this.getReceiver().equals(right)) ||
                (this.getReceiver().equals(left) && this.getSender().equals(right));
    }

    public UUID getOther(UUID uuid){
        return this.getSender().equals(uuid) ? this.getReceiver() : this.getSender();
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
