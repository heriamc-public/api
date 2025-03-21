package fr.heriamc.proxy.packet;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import com.velocitypowered.api.proxy.server.ServerInfo;
import fr.heriamc.api.messaging.packet.HeriaPacket;
import fr.heriamc.api.messaging.packet.HeriaPacketReceiver;
import fr.heriamc.proxy.HeriaProxy;
import fr.heriamc.proxy.packet.model.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import java.net.InetSocketAddress;

public class ProxyPacketReceiver implements HeriaPacketReceiver {

    private final HeriaProxy proxy;

    public ProxyPacketReceiver(HeriaProxy heriaProxy) {
        this.proxy = heriaProxy;
    }

    @Override
    public void execute(String channel, HeriaPacket packet) {
        if(packet instanceof SendPlayerPacket found){
            Player player = proxy.getServer().getPlayer(found.getPlayerUUID()).orElse(null);
            RegisteredServer server = proxy.getServer().getServer(found.getServerTarget()).orElse(null);

            if(server == null || player == null){
                return;
            }

            player.createConnectionRequest(server).connect();
        }

        if(packet instanceof ServerRegisterPacket found){
            ServerInfo serverInfo = new ServerInfo(found.getServerName(), new InetSocketAddress(found.getServerPort()));
            this.proxy.getServer().registerServer(serverInfo);
        }

        if(packet instanceof ServerUnregisterPacket found){
            RegisteredServer registeredServer = this.proxy.getServer().getServer(found.getServerName()).orElse(null);

            if(registeredServer == null){
                return;
            }

            this.proxy.getServer().unregisterServer(registeredServer.getServerInfo());
        }

        if(packet instanceof ProxyPlayerMessagePacket found){
            Player player = proxy.getServer().getPlayer(found.getPlayer()).orElse(null);
            String message = found.getMessage();

            if(player == null || message == null){
                return;
            }

            Component component;

            try {
                component = GsonComponentSerializer.gson().deserialize(message);
            } catch (Exception e){
                component = PlainTextComponentSerializer.plainText().deserialize(message);
            }

            player.sendMessage(component);
        }

        if(packet instanceof ProxyPlayerKickPacket found){
            Player player = proxy.getServer().getPlayer(found.getPlayer()).orElse(null);
            String message = found.getReason();

            if(player == null || message == null){
                return;
            }

            Component component;

            try {
                component = GsonComponentSerializer.gson().deserialize(message);
            } catch (Exception e){
                component = PlainTextComponentSerializer.plainText().deserialize(message);
            }

            player.disconnect(component);
        }
    }

}
