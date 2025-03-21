package fr.heriamc.proxy.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import com.velocitypowered.api.network.ProtocolVersion;
import com.velocitypowered.api.proxy.server.ServerPing;
import fr.heriamc.proxy.HeriaProxy;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class ProxyPingListener {

    private final HeriaProxy proxy;

    private static final String SERVER_DESCRIPTION =
            "  §6§l«§b-§6§l» HeriaMC §8▪ §eServeur Mini-Jeux §8(§b1.8+§8) §6§l«§b-§6§l»\n" +
            "     §8- §cServeur en maintenance temporaire §8-";

    private static final int MAX_PLAYERS = 500;


    public ProxyPingListener(HeriaProxy proxy) {
        this.proxy = proxy;
    }

    @Subscribe
    public void onPlayerPing(ProxyPingEvent event){
        ServerPing ping = event.getPing();

        ServerPing newPing = ping.asBuilder().maximumPlayers(MAX_PLAYERS)
                .description(PlainTextComponentSerializer.plainText().deserialize(SERVER_DESCRIPTION))
                .build();

        event.setPing(newPing);
    }

}
