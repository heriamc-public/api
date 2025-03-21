package fr.heriamc.api.web;

import fr.heriamc.api.HeriaAPI;
import fr.heriamc.api.messaging.packet.HeriaPacket;
import fr.heriamc.api.messaging.packet.HeriaPacketReceiver;

public class WebListener implements HeriaPacketReceiver {

    private final HeriaAPI api;

    public WebListener(HeriaAPI api) {
        this.api = api;
    }

    @Override
    public void execute(String channel, HeriaPacket packet) {
        if(packet instanceof WebCreditsPacket found){
            System.out.println("FOUND WEB CREDITS PACKET");
            System.out.println("NAME: " + found.getUsername());
            System.out.println("CREDITS: " + found.getCredits());
        }
    }

}
