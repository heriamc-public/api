package fr.heriamc.proxy.pool;

import fr.heriamc.api.game.size.GameSize;
import fr.heriamc.api.messaging.packet.HeriaPacket;
import fr.heriamc.api.messaging.packet.HeriaPacketChannel;
import fr.heriamc.api.messaging.packet.HeriaPacketReceiver;
import fr.heriamc.api.server.HeriaServerType;
import fr.heriamc.proxy.queue.packet.InstanceStopPacket;

import java.util.List;
import java.util.UUID;

public interface HeriaPool extends HeriaPacketReceiver {

    boolean isAvailable();
    List<HeriaPacket> createPackets(UUID players);

    HeriaServerType getServerType();

    GameSize getGameSize();

    void stop();

    @Override
    default void execute(String channel, HeriaPacket packet){
        System.out.println("HeriaPool execute start");
        onPacket(channel, packet);

        if(packet instanceof InstanceStopPacket found){
            System.out.println("Instance stop packt found");
            System.out.println("Instance stop packt found");
            System.out.println("Instance stop packt found");
            System.out.println("Instance stop packt found");
            System.out.println("Instance stop packt found");
            System.out.println("Instance stop packt found");
            System.out.println("Instance stop packt found");
            System.out.println("Instance stop packt found");
            onInstanceStop(found.getName());
        }
    }

    void onPacket(String channel, HeriaPacket packet);

    void onInstanceStop(String instanceName);
}
