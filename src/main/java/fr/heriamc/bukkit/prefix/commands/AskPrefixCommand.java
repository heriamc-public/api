package fr.heriamc.bukkit.prefix.commands;

import fr.heriamc.api.user.rank.HeriaRank;
import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.command.CommandArgs;
import fr.heriamc.bukkit.command.HeriaCommand;
import fr.heriamc.bukkit.prefix.PrefixRequest;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class AskPrefixCommand {

    private final HeriaBukkit bukkit;

    private final static String PREFIX_PATTERN = "([a-zA-Z0-9]|&[a-zA-Z0-9])+";

    public AskPrefixCommand(HeriaBukkit bukkit) {
        this.bukkit = bukkit;
    }

    @HeriaCommand(name = "prefix", power = HeriaRank.CUSTOM, description = "Vous permet de faire une demande de préfixe")
    public void onPrefixCommand(CommandArgs args){
        Player player = args.getPlayer();

        PrefixRequest from = bukkit.getPrefixManager().getFromPlayer(player.getUniqueId());

        if(from != null){
            player.sendMessage("§cVous avez déjà une demande pour le préfixe \"" + from.getTag().replaceAll("&", "§") + "§c\" en cours !");
            return;
        }

        if(args.getArgs().length < 1){
            player.sendMessage("§c/prefix <préfixe>");
            return;
        }

        String arg0 = args.getArgs(0);

        if(arg0.equals("[confirm]")){

            if(args.getArgs().length > 3){
                player.sendMessage("§cVotre préfixe ne peut pas contenir d'espaces, cela doit être un seul mot");
                return;
            }

            String prefix = args.getArgs(1);
            String error = checkPrefix(prefix);

            if(error != null){
                player.sendMessage(error);
                return;
            }

            PrefixRequest loaded = bukkit.getPrefixManager().createOrLoad(UUID.randomUUID());
            loaded.setPlayer(player.getUniqueId());
            loaded.setTag(prefix);

            bukkit.getPrefixManager().save(loaded);
            bukkit.getPrefixManager().saveInPersistant(loaded);
            player.sendMessage("§aVotre demande pour le préfixe \"" + prefix.replaceAll("&", "§") + "§a\" à été demandée ! /prefixstatus pour connaître le status de votre demande.");

            return;
        }

        if(args.getArgs().length > 2){
            player.sendMessage("§cVotre préfixe ne peut pas contenir d'espaces, cela doit être un seul mot");
            return;
        }

        String error = checkPrefix(arg0);

        if(error != null){
            player.sendMessage(error);
            return;
        }

        TextComponent confirm = new TextComponent(TextComponent.fromLegacyText("§a[§a§l✓ §aConfirmer]"));
        confirm.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§aÊtes vous sur de vouloir de soumettre ce préfixe ?")));
        confirm.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/prefix [confirm] " + arg0));

        player.sendMessage(" ");
        player.sendMessage("§cVotre préfixe doit être vérifié par un membre de l'équipe pour vous être appliqué. " +
                "Etes-vous sur que votre préfixe respecte le règlement du serveur ? En cas d'abus, une sanction peut " +
                "vous être appliqué");
        player.spigot().sendMessage(confirm);
        player.sendMessage(" ");

    }

    public String checkPrefix(String prefix){

        if(prefix.length() > 12){
            return "§cVotre préfixe ne peut pas dépasser 12 caractères. (couleurs comprises)";
        }

        if(!prefix.matches(PREFIX_PATTERN)){
            return "§cVotre préfixe ne peut contenir que des lettres, des chiffres et des couleurs.";
        }

        List<String> forbiddenColors = List.of("&k", "&l", "&m", "&n", "&o", "&r", "&8", "&0", "&1");
        for (String forbiddenColor : forbiddenColors) {
            if(prefix.contains(forbiddenColor)){
                return "§cVotre préfixe ne peut pas contenir d'effet de textes, ou de couleurs trop sombres.";
            }
        }

        return null;
    }

    @HeriaCommand(name = "prefixstatus", power = HeriaRank.CUSTOM, description = "Vous permet de vérifier le statut de votre demande de préfixe")
    public void onPrefixStatusCommand(CommandArgs args){
        Player player = args.getPlayer();

        PrefixRequest from = bukkit.getPrefixManager().getFromPlayer(player.getUniqueId());

        if(from == null){
            player.sendMessage("§cVotre demande de préfixe à été refusé ou n'existe pas");
            return;
        }

        player.sendMessage("§6Votre demande pour le préfixe \"" + from.getTag().replaceAll("&", "§") + "§6\" est toujours en cours..");
    }
}
