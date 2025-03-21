package fr.heriamc.proxy.packet.model;

import fr.heriamc.api.messaging.packet.HeriaPacket;
import fr.heriamc.api.messaging.packet.HeriaPacketChannel;

public class ServerUnregisterPacket extends HeriaPacket {

    private final String serverName;

    public ServerUnregisterPacket(String serverName) {
        super(HeriaPacketChannel.API);
        this.serverName = serverName;
    }

    public String getServerName() {
        return serverName;
    }
}
