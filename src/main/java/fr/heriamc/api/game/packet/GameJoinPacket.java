package fr.heriamc.api.game.packet;

import fr.heriamc.api.messaging.packet.HeriaPacket;
import fr.heriamc.api.messaging.packet.HeriaPacketChannel;

import java.util.UUID;

public class GameJoinPacket extends HeriaPacket {

    private final UUID uuid;
    private final String gameName;
    private final boolean spectator;

    public GameJoinPacket(UUID uuid, String gameName, boolean spectator) {
        super(HeriaPacketChannel.GAME);
        this.uuid = uuid;
        this.gameName = gameName;
        this.spectator = spectator;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getGameName() {
        return gameName;
    }

    public boolean isSpectator() {
        return spectator;
    }
}