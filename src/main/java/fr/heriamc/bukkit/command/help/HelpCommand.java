package fr.heriamc.bukkit.command.help;

import fr.heriamc.api.user.rank.HeriaRank;
import fr.heriamc.bukkit.command.CommandArgs;
import fr.heriamc.bukkit.command.HeriaCommand;
import fr.heriamc.bukkit.menu.pagination.Pagination;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HelpCommand {

    private final Map<HeriaCommand, HeriaRank> commandMap;
    private static final int COMMANDS_PER_PAGE = 5;

    public HelpCommand(Map<HeriaCommand, HeriaRank> commandMap) {
        this.commandMap = commandMap;
    }

    @HeriaCommand(name = "help", power = HeriaRank.PLAYER, description = "Vous affiche ce message d'aide")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        int page = 1;
        int maxPage = getMaxPage();

        try {
            page = Integer.parseInt(args.getArgs(0));
            page = Math.max(1, Math.min(page, maxPage));
        } catch (Exception ignored) {

        }

        displayHelp(player, page);
    }

    private void displayHelp(Player sender, int page) {
        Pagination<HeriaCommand> pagination = getPagination();
        List<HeriaCommand> pageContent = pagination.getPageContent(page - 1);

        sender.sendMessage(" ");
        sender.sendMessage("§7Aide de HeriaMC (Page " + page + "/" + getMaxPage() + ") :");

        for (HeriaCommand heriaCommand : pageContent) {
            sender.sendMessage(" §8■ §f/" + heriaCommand.name() + " §8(§7" + heriaCommand.description() + "§8)");
        }

        boolean isFirstPage = page == 1;
        boolean isLastPage = page == getMaxPage();

        TextComponent before = new TextComponent((isFirstPage ? "§7" : "§6") + "[Page précédente] ");
        if (!isFirstPage) {
            before.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§8« §7Page précédente")));
            before.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/help " + (page - 1)));
        }

        TextComponent after = new TextComponent((isLastPage ? "§7" : "§6") + "[Page suivante]");
        if (!isLastPage) {
            after.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§8» §7Page suivante")));
            after.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/help " + (page + 1)));
        }

        sender.spigot().sendMessage(before, after);
        sender.sendMessage(" ");
    }

    private int getMaxPage() {
        return (getCommands().size() + COMMANDS_PER_PAGE - 1) / COMMANDS_PER_PAGE;
    }

    public Pagination<HeriaCommand> getPagination() {
        return new Pagination<>(COMMANDS_PER_PAGE, getCommands());
    }

    public List<HeriaCommand> getCommands() {
        List<HeriaCommand> out = new ArrayList<>();
        for (Map.Entry<HeriaCommand, HeriaRank> entry : this.commandMap.entrySet()) {
            HeriaCommand command = entry.getKey();

            if(command.name().contains(".")){
                continue;
            }

            if(!command.showInHelp()){
                continue;
            }

            if (command.power().getPower() <= HeriaRank.CUSTOM.getPower()) {
                out.add(command);
            }
        }
        return out;
    }
}