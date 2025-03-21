package fr.heriamc.bukkit.friends;

import fr.heriamc.api.friends.HeriaFriendLink;
import fr.heriamc.api.friends.HeriaFriendLinkStatus;
import fr.heriamc.api.user.HeriaPlayer;
import fr.heriamc.api.user.rank.HeriaRank;
import fr.heriamc.api.user.resolver.HeriaPlayerResolver;
import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.command.CommandArgs;
import fr.heriamc.bukkit.command.HeriaCommand;
import fr.heriamc.bukkit.packet.BukkitPlayerMessagePacket;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class FriendCommands {

    private final HeriaBukkit bukkit;

    public FriendCommands(HeriaBukkit bukkit) {
        this.bukkit = bukkit;
    }

    @HeriaCommand(name = "friend", inGameOnly = true, power = HeriaRank.PLAYER, aliases = {"f", "friends"}, description = "Vous permet de gérer des amis")
    public void onFriendCommand(CommandArgs args){
        Player player = args.getPlayer();

        player.sendMessage(" ");
        player.sendMessage("§fAide pour la commande §6/friend§f:");
        player.sendMessage(" §6» §e/friend add <joueur>§f: Ajouter/Accepter un ami");
        player.sendMessage(" §6» §e/friend remove <joueur>§f: Retirer un ami");
        player.sendMessage(" §6» §e/friend list §f: Voir la liste de ses amis");
        player.sendMessage(" ");
    }

    @HeriaCommand(name = "friend.add", inGameOnly = true, power = HeriaRank.PLAYER, aliases = {"f.add", "friends.add"})
    public void onFriendAddCommand(CommandArgs args) {
        Player player = args.getPlayer();
        HeriaPlayer heriaPlayer = this.bukkit.getApi().getPlayerManager().get(player.getUniqueId());

        if (args.length() != 1) {
            player.sendMessage("§c/friend add <joueur>");
            return;
        }

        String targetName = args.getArgs(0);
        HeriaPlayerResolver targetResolver = bukkit.getApi().getResolverManager().get(targetName);

        if (targetResolver == null) {
            player.sendMessage("§cCe joueur n'existe pas");
            return;
        }

        HeriaPlayer target = bukkit.getApi().getPlayerManager().get(targetResolver.getUuid());
        List<HeriaFriendLink> friendLinks = bukkit.getFriendLinkManager().getFromPlayer(target.getId());

        HeriaFriendLink active = null;

        for (HeriaFriendLink friendLink : friendLinks) {
            if(friendLink.hasLink(heriaPlayer.getId(), target.getId())){
                active = friendLink;
            }
        }

        if(active != null){
            if(active.getStatus() == HeriaFriendLinkStatus.ACTIVE){
                player.sendMessage("§cVous êtes déjà ami avec ce joueur.");

                return;
            }
            if(active.getReceiver().equals(heriaPlayer.getId())){
                player.sendMessage("§aVous avez accepté la demande d'ami de " + target.getName());
                active.setStatus(HeriaFriendLinkStatus.ACTIVE);
                bukkit.getFriendLinkManager().save(active);

                this.bukkit.getApi().getMessaging().send(new BukkitPlayerMessagePacket(target.getId(), "§a" + heriaPlayer.getName() + " a accepté votre demande d'ami !"));
                return;
            }

            player.sendMessage("§cVous avez déjà une demande d'ami envoyée à ce joueur");

            return;
        }

        HeriaFriendLink loaded = bukkit.getFriendLinkManager().createOrLoad(UUID.randomUUID());

        loaded.setReceiver(target.getId());
        loaded.setSender(heriaPlayer.getId());

        bukkit.getFriendLinkManager().saveInPersistant(loaded);
        bukkit.getFriendLinkManager().save(loaded);

        player.sendMessage("§aVotre demande d'ami pour " + target.getName() + " a bien été envoyée !");

        TextComponent mainText = new TextComponent("§fVous avez reçu une demande d'ami de §6" + player.getName() + "§f.\n");

        TextComponent accept = new TextComponent("§a[§a§l✓ §aAccepter] ");
        accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§aDevenir ami avec " + player.getName() + "?")));
        accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend add " + player.getName()));

        TextComponent refuse = new TextComponent("§c[§c§l✗ §cRefuser]");
        refuse.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§cRefuser la demande de " + player.getName() + "?")));
        refuse.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend refuse " + player.getName()));


        this.bukkit.getApi().getMessaging().send(new BukkitPlayerMessagePacket(target.getId(), ComponentSerializer.toString(mainText,accept,refuse)));
    }

    @HeriaCommand(name = "friend.remove", inGameOnly = true, power = HeriaRank.PLAYER, aliases = {"f.remove", "friends.remove"})
    public void onFriendRemoveCommand(CommandArgs args){

    }

    @HeriaCommand(name = "friend.refuse", inGameOnly = true, power = HeriaRank.PLAYER, aliases = {"f.refuse", "friends.refuse"})
    public void onFriendRefuseCommand(CommandArgs args){

    }


    @HeriaCommand(name = "friend.list", inGameOnly = true, power = HeriaRank.PLAYER, aliases = {"f.list", "friends.list"})
    public void onFriendListCommand(CommandArgs args){
        Player player = args.getPlayer();
        bukkit.getMenuManager().open(new FriendsMenu(player, bukkit, bukkit.getApi().getPlayerManager().get(player.getUniqueId())));
    }

}
