package fr.heriamc.bukkit.chat;

import fr.heriamc.api.sanction.HeriaSanction;
import fr.heriamc.api.sanction.HeriaSanctionType;
import fr.heriamc.api.user.HeriaPlayer;
import fr.heriamc.api.user.rank.HeriaRank;
import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.event.HeriaChatEvent;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.*;

public class HeriaChatListener implements Listener {

    private final HeriaBukkit bukkit;

    public HeriaChatListener(HeriaBukkit bukkit) {
        this.bukkit = bukkit;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e){
        Player player = e.getPlayer();
        UUID chatID = UUID.randomUUID();

        HeriaChatMessage chat = new HeriaChatMessage(chatID, player.getUniqueId(), e.getMessage(), false);
        bukkit.getChatManager().put(chat);

        if(e.isCancelled()){
            return;
        }

        HeriaPlayer heriaPlayer = bukkit.getApi().getPlayerManager().get(player.getUniqueId());

        List<HeriaSanction> mutes = bukkit.getApi().getSanctionManager().getActiveSanctions(heriaPlayer.getId(), HeriaSanctionType.MUTE);

        if(!mutes.isEmpty()){
            HeriaSanction mute = mutes.get(0);

            for (String s : bukkit.getApi().getSanctionManager().getMuteMessage(mute)) {
                player.sendMessage(s);
            }

            e.setCancelled(true);
            return;
        }


        TextComponent reportSymbol = new TextComponent(TextComponent.fromLegacyText("⚠"));
        reportSymbol.setColor(ChatColor.RED);
        reportSymbol.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/reportchat " + chatID));
        reportSymbol.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder("Cliquez pour signaler " + heriaPlayer.getNickedName()).color(ChatColor.RED).create()));

        String displayed = null;

        if(heriaPlayer.isRemovedTag()){
            displayed = HeriaRank.DEFAULT.getPrefix();
        } else {
            if(heriaPlayer.getCustomPrefix() != null){
                displayed = heriaPlayer.getCustomPrefix().replaceAll("&", "§") + " ";
            } else if(heriaPlayer.isNicked()){
                HeriaRank rank = heriaPlayer.getNickData().getNewRank();
                if(rank != null){
                    displayed = rank.getPrefix();
                }
            }

            if(displayed == null){
                displayed = heriaPlayer.getRank().getPrefix();
            }
        }

        String message = e.getMessage();

        if(heriaPlayer.getRank().getPower() >= HeriaRank.SUPREME.getPower()){
            message = message.replaceAll("(?i)(?<!\\p{L})(gg)(?!\\p{L})", "§6§lGG§r");
        }

        String color = "§f";
        if(heriaPlayer.isMod()){
            color = "§b";
        }

        TextComponent messageComponent = new TextComponent(TextComponent.fromLegacyText(" " + displayed + heriaPlayer.getNickedName() +" §8» " + color + message));

        HeriaChatEvent chatEvent = new HeriaChatEvent(player, heriaPlayer, heriaPlayer.getNickedName(), message, displayed, reportSymbol, messageComponent);
        bukkit.getServer().getPluginManager().callEvent(chatEvent);

        if(chatEvent.isCancelled()){
            e.setCancelled(true);
            return;
        }

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.spigot().sendMessage(reportSymbol, messageComponent);
        }

        e.setCancelled(true);
    }

    /*@EventHandler
    public void onTabComplete(PlayerChatTabCompleteEvent event){
        Player player = event.getPlayer();
        Collection<String> tabCompletions = event.getTabCompletions();

        HeriaPlayer heriaPlayer = bukkit.getApi().getPlayerManager().get(player.getUniqueId());

        System.out.println("completions= " + tabCompletions);
        if(tabCompletions.contains("whitelist")){
            List<String> completed = new ArrayList<>();

            for (Map.Entry<String, HeriaRank> entry : bukkit.getCommandManager().getPermissionsCommands().entrySet()) {
                String name = entry.getKey();
                HeriaRank power = entry.getValue();



                if(heriaPlayer.getRank().getPower() >= power.getPower()){
                    completed.add(name);
                }

            }

            event.getTabCompletions().clear();
            event.getTabCompletions().addAll(completed);
        }
    }*/
}
