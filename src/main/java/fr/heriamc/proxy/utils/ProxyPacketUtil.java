package fr.heriamc.proxy.utils;

import fr.heriamc.api.game.packet.GameJoinPacket;
import fr.heriamc.api.messaging.packet.HeriaPacket;
import fr.heriamc.proxy.packet.model.SendPlayerPacket;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class ProxyPacketUtil {

    public static List<HeriaPacket> buildJoinServer(UUID player, String server){
        return List.of(new SendPlayerPacket(player, server));
    }

    public static List<HeriaPacket> buildJoinGame(UUID player, String sever, String game, boolean spectator){
        List<HeriaPacket> packets = new ArrayList<>(List.of(new GameJoinPacket(player, game, spectator)));
        packets.addAll(buildJoinServer(player, sever));
        return packets;
    }

}
