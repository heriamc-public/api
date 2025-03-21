package fr.heriamc.bukkit.report;

import fr.heriamc.api.data.SerializableData;

import java.util.Date;
import java.util.UUID;

public class HeriaReport implements SerializableData<UUID> {

    private UUID id;
    private HeriaReportType type;
    private UUID sender;
    private UUID target;
    private String reason;
    private Date instant;

    public HeriaReport(UUID id, HeriaReportType type, UUID sender, UUID target, String reason, Date instant) {
        this.id = id;
        this.type = type;
        this.sender = sender;
        this.target = target;
        this.reason = reason;
        this.instant = instant;
    }

    public UUID getId() {
        return id;
    }

    public HeriaReport setId(UUID id) {
        this.id = id;
        return this;
    }

    public HeriaReportType getType() {
        return type;
    }

    public HeriaReport setType(HeriaReportType type) {
        this.type = type;
        return this;
    }

    public UUID getSender() {
        return sender;
    }

    public HeriaReport setSender(UUID sender) {
        this.sender = sender;
        return this;
    }

    public UUID getTarget() {
        return target;
    }

    public HeriaReport setTarget(UUID target) {
        this.target = target;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public HeriaReport setReason(String reason) {
        this.reason = reason;
        return this;
    }

    public Date getInstant() {
        return instant;
    }

    public HeriaReport setInstant(Date instant) {
        this.instant = instant;
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
