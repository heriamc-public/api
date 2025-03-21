package fr.heriamc.proxy.packet.model;

import fr.heriamc.api.messaging.packet.HeriaPacket;
import fr.heriamc.api.messaging.packet.HeriaPacketChannel;

public class ServerRegisterPacket extends HeriaPacket {

    private final String serverName;
    private final int serverPort;

    public ServerRegisterPacket(String serverName, int serverPort) {
        super(HeriaPacketChannel.API);
        this.serverName = serverName;
        this.serverPort = serverPort;
    }

    public String getServerName() {
        return serverName;
    }

    public int getServerPort() {
        return serverPort;
    }
}
