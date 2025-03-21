package fr.heriamc.proxy.listeners;

import com.velocitypowered.api.event.ResultedEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.event.player.*;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import com.velocitypowered.api.proxy.server.ServerPing;
import com.velocitypowered.api.util.GameProfile;
import fr.heriamc.api.sanction.HeriaSanction;
import fr.heriamc.api.sanction.HeriaSanctionType;
import fr.heriamc.api.server.HeriaServer;
import fr.heriamc.api.server.HeriaServerType;
import fr.heriamc.api.user.HeriaPlayer;
import fr.heriamc.api.user.resolver.HeriaPlayerResolver;
import fr.heriamc.api.user.unlock.HeriaUnlockable;
import fr.heriamc.proxy.HeriaProxy;
import fr.heriamc.proxy.queue.HeriaQueueHandler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import java.util.List;
import java.util.UUID;

public class ProxyPlayerListener {

    private final HeriaProxy proxy;

    public ProxyPlayerListener(HeriaProxy proxy) {
        this.proxy = proxy;
    }



    @Subscribe
    public void onLogin(LoginEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        HeriaPlayer loaded = this.proxy.getApi().getPlayerManager().createOrLoad(uuid);
        loaded.setName(player.getUsername());
        this.proxy.getApi().getPlayerManager().save(loaded);

        HeriaPlayerResolver resolver = this.proxy.getApi().getResolverManager().createOrLoad(player.getUsername().toLowerCase());

        if(resolver.getUuid() == null){
            resolver.setUuid(player.getUniqueId());
            this.proxy.getApi().getResolverManager().saveInPersistant(resolver);
            this.proxy.getApi().getResolverManager().save(resolver);
        }

        this.proxy.getApi().getSettingsManager().createOrLoad(player.getUniqueId());
        this.proxy.getApi().getUnlockableManager().createOrLoad(player.getUniqueId());

        List<HeriaSanction> bans = this.proxy.getApi().getSanctionManager().getActiveSanctions(uuid, HeriaSanctionType.BAN);
        if(!bans.isEmpty()){
            HeriaSanction active = bans.get(0);

            Component banComponent = PlainTextComponentSerializer.plainText().deserialize(
                    this.proxy.getApi().getSanctionManager().getBanMessage(active));

            event.setResult(ResultedEvent.ComponentResult.denied(banComponent));
            event.getPlayer().disconnect(banComponent);
            return;
        }

        // maintenance
        if(loaded.getRank().getPower() < 10) {
            Component component = Component.text("Vous n'êtes pas dans la liste blanche du serveur");
            event.setResult(ResultedEvent.ComponentResult.denied(component));
            event.getPlayer().disconnect(component);
            return;
        }
    }

    @Subscribe
    public void onGameProfile(GameProfileRequestEvent e){
        if(e.isOnlineMode()){
            return;
        }

        // GROS BIG-UP A SUPER_CRAFTING

        GameProfile originalProfile = e.getGameProfile();
        OfflineModeSkin skin = OfflineModeSkin.random();

        originalProfile = originalProfile.addProperty(new GameProfile.Property("textures", skin.getValue(), skin.getSignature()));

        e.setGameProfile(originalProfile);
    }

    @Subscribe
    public void onClientBrand(PlayerClientBrandEvent event){
        Player player = event.getPlayer();
        HeriaPlayer cached = this.proxy.getApi().getPlayerManager().getInCache(player.getUniqueId());

        if(cached != null){
            cached.setClientBrand(event.getBrand());
            if(event.getBrand() == null) cached.setClientBrand("Inconnu (cheater probablement?)");

            this.proxy.getApi().getPlayerManager().save(cached);
        }
    }

    @Subscribe
    public void onDisconnect(DisconnectEvent event){
        Player player = event.getPlayer();

        HeriaPlayer cached = this.proxy.getApi().getPlayerManager().getInCache(player.getUniqueId());

        if(cached != null){
            System.out.println("Le joueur " + player.getUsername() + " s'est déconnecté et a été trouvé dans le cache. sauvegarde de son profil...");

            cached.setConnectedTo(null);

            this.proxy.getApi().getPlayerManager().saveInPersistant(cached);
            this.proxy.getApi().getPlayerManager().remove(cached.getIdentifier());

            if(cached.isInQueue()){
                HeriaQueueHandler handler = proxy.getQueueManager().getHandler(player.getUniqueId());

                if(handler != null){
                    handler.removePlayer(player.getUniqueId());
                }
            }
            System.out.println("Sauvegarde réussie.");
        }

        HeriaUnlockable unlockable = this.proxy.getApi().getUnlockableManager().getInCache(player.getUniqueId());

        if(unlockable != null){
            this.proxy.getApi().getUnlockableManager().saveInPersistant(unlockable);
            this.proxy.getApi().getUnlockableManager().remove(unlockable);
        }


    }


}
