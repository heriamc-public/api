package fr.heriamc.api.server;

import fr.heriamc.api.data.SerializableData;

import java.util.List;
import java.util.UUID;

public class HeriaServer implements SerializableData<String> {

    private String name;
    private HeriaServerType type;
    private HeriaServerStatus status;
    private UUID host;
    private int port;
    private long creation;
    private List<UUID> connected;

    public HeriaServer(String name, HeriaServerType type, HeriaServerStatus status, UUID host, int port, long creation, List<UUID> connected) {
        this.name = name;
        this.type = type;
        this.status = status;
        this.host = host;
        this.port = port;
        this.creation = creation;
        this.connected = connected;
    }

    public String getName() {
        return name;
    }

    public HeriaServer setName(String name) {
        this.name = name;
        return this;
    }

    public HeriaServerType getType() {
        return type;
    }

    public HeriaServer setType(HeriaServerType type) {
        this.type = type;
        return this;
    }

    public HeriaServerStatus getStatus() {
        return status;
    }

    public HeriaServer setStatus(HeriaServerStatus status) {
        this.status = status;
        return this;
    }

    public UUID getHost() {
        return host;
    }

    public HeriaServer setHost(UUID host) {
        this.host = host;
        return this;
    }

    public int getPort() {
        return port;
    }

    public HeriaServer setPort(int port) {
        this.port = port;
        return this;
    }

    public long getCreation() {
        return creation;
    }

    public HeriaServer setCreation(long creation) {
        this.creation = creation;
        return this;
    }

    public List<UUID> getConnected() {
        return connected;
    }

    public HeriaServer setConnected(List<UUID> connected) {
        this.connected = connected;
        return this;
    }

    public int getConnectedCount(){
        return this.connected.size();
    }

    @Override
    public String getIdentifier() {
        return this.name;
    }

    @Override
    public void setIdentifier(String identifier) {
        this.name = identifier;
    }
}
