package fr.heriamc.bukkit.mod;

import fr.heriamc.api.user.HeriaPlayer;
import fr.heriamc.bukkit.HeriaBukkit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

public class ModListener implements Listener {

    private final ModManager modManager;
    private final HeriaBukkit bukkit;

    public ModListener(ModManager modManager, HeriaBukkit bukkit) {
        this.modManager = modManager;
        this.bukkit = bukkit;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        HeriaPlayer heriaPlayer = bukkit.getApi().getPlayerManager().get(player.getUniqueId());

        if(heriaPlayer.isMod()){;
            this.modManager.setVanished(player, heriaPlayer, heriaPlayer.isVanished());
        } else {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                HeriaPlayer heriaOnline = bukkit.getApi().getPlayerManager().get(onlinePlayer.getUniqueId());
                if(heriaOnline.isVanished()){
                    player.hidePlayer(onlinePlayer);
                }
            }
        }


    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player player = e.getPlayer();
        HeriaPlayer heriaPlayer = bukkit.getApi().getPlayerManager().get(player.getUniqueId());

        if(!heriaPlayer.isMod()){
            return;
        }

        ModItem modItem = ModItem.getFromStack(e.getItem());

        if(modItem == null){
            return;
        }

        modItem.onInteract(modManager, player);
    }

    @EventHandler
    public void onInteractAtEntity(PlayerInteractAtEntityEvent e){
        Player player = e.getPlayer();

        if(!(e.getRightClicked() instanceof Player rightClicked)){
            return;
        }

        HeriaPlayer heriaPlayer = bukkit.getApi().getPlayerManager().get(player.getUniqueId());

        if(!heriaPlayer.isMod()){
            return;
        }

        ModItem modItem = ModItem.getFromStack(e.getPlayer().getItemInHand());

        if(modItem == null){
            return;
        }

        modItem.onInteractOnPlayer(bukkit, player, rightClicked);
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e){
        Player player = e.getPlayer();

        HeriaPlayer heriaPlayer = bukkit.getApi().getPlayerManager().get(player.getUniqueId());

        if(!heriaPlayer.isMod()){
            return;
        }

        ModItem modItem = ModItem.getFromStack(e.getItemDrop().getItemStack());

        if(modItem == null){
            return;
        }

        e.setCancelled(true);
    }

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent e){
        Player player = e.getPlayer();

        HeriaPlayer heriaPlayer = bukkit.getApi().getPlayerManager().get(player.getUniqueId());

        if(!heriaPlayer.isMod()){
            return;
        }

        e.setCancelled(true);
    }

}
