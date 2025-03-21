package fr.heriamc.api.web;

import fr.heriamc.api.messaging.packet.HeriaPacket;
import fr.heriamc.api.messaging.packet.HeriaPacketChannel;

public class WebCreditsPacket extends HeriaPacket {

    private final String username;
    private final float credits;

    public WebCreditsPacket(String username, float credits) {
        super(HeriaPacketChannel.WEB);
        this.username = username;
        this.credits = credits;
    }

    public String getUsername() {
        return username;
    }

    public float getCredits() {
        return credits;
    }
}
