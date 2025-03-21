package fr.heriamc.api.messaging.packet;

public interface HeriaPacketReceiver {

    void execute(String channel, HeriaPacket packet);

}
