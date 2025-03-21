package fr.heriamc.bukkit.packet;

import fr.heriamc.api.messaging.packet.HeriaPacket;
import fr.heriamc.api.messaging.packet.HeriaPacketReceiver;
import fr.heriamc.api.user.HeriaPlayer;
import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.api.game.packet.GameCreatedPacket;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashSet;

public class BukkitPacketReceiver implements HeriaPacketReceiver {

    private final HeriaBukkit bukkit;

    public BukkitPacketReceiver(HeriaBukkit bukkit) {
        this.bukkit = bukkit;
    }

    @Override
    public void execute(String channel, HeriaPacket packet) {

        if(packet instanceof BukkitPlayerMessagePacket found){
            Player player = Bukkit.getPlayer(found.getPlayer());
            String message = found.getMessage();

            if(player == null || message == null){
                return;
            }

            try {
                System.out.println("message: " + message);
                BaseComponent[] components = ComponentSerializer.parse(message);

                if(components == null){
                    throw new NullPointerException("components null");
                }

                for (BaseComponent component : components) {
                    ClickEvent clickEvent = component.getClickEvent();
                    System.out.println("clickEvent: " + clickEvent);
                }

                player.spigot().sendMessage(components);
            } catch (Exception e){
                player.sendMessage(message);
            }
        }

        if(packet instanceof BukkitBroadcastMessagePacket found){
            Collection<? extends Player> viewers = new HashSet<>(bukkit.getServer().getOnlinePlayers());
            int neededPower = found.getNeededPower();

            if(neededPower != 0){

                for (Player onlinePlayer : bukkit.getServer().getOnlinePlayers()) {
                    HeriaPlayer heriaPlayer = bukkit.getApi().getPlayerManager().get(onlinePlayer.getUniqueId());

                    if(heriaPlayer.getRank().getPower() < neededPower){
                        viewers.remove(onlinePlayer);
                    }
                }
            }

            for (Player viewer : viewers) {
                viewer.sendMessage(found.getMessage());
            }
        }

        if(packet instanceof GameCreatedPacket found){
            System.out.println(found);
        }
    }

}
