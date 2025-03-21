package fr.heriamc.proxy.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.KickedFromServerEvent;
import com.velocitypowered.api.event.player.PlayerChooseInitialServerEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import fr.heriamc.api.server.HeriaServer;
import fr.heriamc.api.server.HeriaServerType;
import fr.heriamc.api.user.HeriaPlayer;
import fr.heriamc.proxy.HeriaProxy;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class ProxyServerListener {

    private final HeriaProxy proxy;

    public ProxyServerListener(HeriaProxy proxy) {
        this.proxy = proxy;
    }

    @Subscribe
    public void onServerChoose(PlayerChooseInitialServerEvent e){
        HeriaServer server = this.proxy.getApi().getServerManager().getReadyWithLessPlayers(HeriaServerType.HUB);

        if(server == null){
            e.getPlayer().disconnect(Component.text("§cAucun serveur hub n'a été trouvé"));
            e.setInitialServer(null);
            return;
        }

        RegisteredServer registeredServer = this.proxy.getServer().getServer(server.getName()).orElse(null);
        e.setInitialServer(registeredServer);
    }

    @Subscribe
    public void onServerJoin(ServerConnectedEvent e){
        Player player = e.getPlayer();
        String name = e.getServer().getServerInfo().getName();

        HeriaPlayer heriaPlayer = proxy.getApi().getPlayerManager().get(player.getUniqueId());

        if(heriaPlayer == null){
            return;
        }

        heriaPlayer.setConnectedTo(name);
        proxy.getApi().getPlayerManager().save(heriaPlayer);

        HeriaServer heriaServer = proxy.getApi().getServerManager().get(name);

        if(heriaServer == null) {
            return;
        }

        heriaServer.getConnected().add(player.getUniqueId());
        proxy.getApi().getServerManager().save(heriaServer);
    }

    @Subscribe
    public void onKick(KickedFromServerEvent event){
        if(!(event.getResult() instanceof KickedFromServerEvent.RedirectPlayer playerRedirection)){
            return;
        }

        HeriaServer server = proxy.getApi().getServerManager().getWithLessPlayers(HeriaServerType.HUB);
        RegisteredServer registeredServer = proxy.getServer().getServer(server.getName()).orElse(null);

        if(registeredServer == null){
            return;
        }

        event.setResult(KickedFromServerEvent.RedirectPlayer.create(registeredServer, Component.text("Votre serveur précédent a rencontré un problème, vous avez été redirigé vers " + server.getName(), NamedTextColor.RED)));
    }
}
