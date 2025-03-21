package fr.heriamc.bukkit.command;

import fr.heriamc.api.HeriaAPI;
import fr.heriamc.api.user.HeriaPlayer;
import fr.heriamc.api.user.rank.HeriaRank;
import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.command.help.HelpCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;

public class HeriaCommandManager implements CommandExecutor {

    private final Map<String, Entry<Method, Object>> commandMap = new HashMap<String, Entry<Method, Object>>();
    private CommandMap map;
    private final Plugin plugin;

    private final Map<HeriaCommand, HeriaRank> permissionsCommands = new HashMap<>();

    public HeriaCommandManager(Plugin plugin) {
        this.plugin = plugin;
        if (plugin.getServer().getPluginManager() instanceof SimplePluginManager) {
            SimplePluginManager manager = (SimplePluginManager) plugin.getServer().getPluginManager();
            try {
                Field field = SimplePluginManager.class.getDeclaredField("commandMap");
                field.setAccessible(true);
                map = (CommandMap) field.get(manager);
            } catch (IllegalArgumentException | NoSuchFieldException | IllegalAccessException | SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        return handleCommand(sender, cmd, label, args);
    }

    public boolean handleCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        for (int i = args.length; i >= 0; i--) {
            StringBuilder buffer = new StringBuilder();
            buffer.append(label.toLowerCase());
            for (int x = 0; x < i; x++) {
                buffer.append(".").append(args[x].toLowerCase());
            }
            String cmdLabel = buffer.toString();
            if (commandMap.containsKey(cmdLabel)) {
                Method method = commandMap.get(cmdLabel).getKey();
                Object methodObject = commandMap.get(cmdLabel).getValue();
                HeriaCommand command = method.getAnnotation(HeriaCommand.class);

                // Remove permissions system
                /*if (!command.permission().equals("") && !sender.hasPermission(command.permission())) {
                    sender.sendMessage(command.noPerm());
                    return true;
                }*/

                if (command.inGameOnly() && !(sender instanceof Player)) {
                    sender.sendMessage("This command is only performable in game");
                    return true;
                }

                // HeriaMC start power rank permission system
                Player player = (Player) sender;

                HeriaPlayer heriaPlayer = HeriaAPI.get().getPlayerManager().get(player.getUniqueId());
                int rankPower = heriaPlayer.getRank().getPower();

                if(command.power().getPower() > rankPower){
                    sender.sendMessage("§6§lHeriaMC §8┃ §cAction non autorisée. (" + command.power().getColor() + command.power().getName().toUpperCase() + ")");
                    return true;
                }

                // HeriaMC end


                try {
                    method.invoke(methodObject, new CommandArgs(sender, cmd, label, args,
                            cmdLabel.split("\\.").length - 1));
                } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }
        defaultCommand(new CommandArgs(sender, cmd, label, args, 0));
        return true;
    }

    public void registerCommand(Object obj) {
        for (Method m : obj.getClass().getMethods()) {
            if (m.getAnnotation(HeriaCommand.class) != null) {
                HeriaCommand command = m.getAnnotation(HeriaCommand.class);
                if (m.getParameterTypes().length > 1 || m.getParameterTypes()[0] != CommandArgs.class) {
                    System.out.println("Unable to register command " + m.getName() + ". Unexpected method arguments");
                    continue;
                }

                permissionsCommands.put(command, command.power());

                registerCommand0(command, command.name(), m, obj);
                for (String alias : command.aliases()) {
                    registerCommand0(command, alias, m, obj);
                }
            } else if (m.getAnnotation(HeriaCompleter.class) != null) {
                HeriaCompleter comp = m.getAnnotation(HeriaCompleter.class);
                if (m.getParameterTypes().length != 1
                        || m.getParameterTypes()[0] != CommandArgs.class) {
                    System.out.println("Unable to register tab completer " + m.getName()
                            + ". Unexpected method arguments");
                    continue;
                }
                if (m.getReturnType() != List.class) {
                    System.out.println("Unable to register tab completer " + m.getName() + ". Unexpected return type");
                    continue;
                }
                registerCompleter0(comp.name(), m, obj);
                for (String alias : comp.aliases()) {
                    registerCompleter0(alias, m, obj);
                }
            }
        }
    }

    // Register help remove
    /*public void registerHelp() {
        Set<HelpTopic> help = new TreeSet<HelpTopic>(HelpTopicComparator.helpTopicComparatorInstance());
        for (String s : commandMap.keySet()) {
            if (!s.contains(".")) {
                org.bukkit.command.Command cmd = map.getCommand(s);
                HelpTopic topic = new GenericCommandHelpTopic(cmd);
                help.add(topic);
            }
        }
        IndexHelpTopic topic = new IndexHelpTopic(plugin.getName(), "All commands for " + plugin.getName(), null, help,
                "Below is a list of all " + plugin.getName() + " commands:");
        Bukkit.getServer().getHelpMap().addTopic(topic);
    }*/

    public void registerCommand0(HeriaCommand command, String label, Method m, Object obj) {
        commandMap.put(label.toLowerCase(), new AbstractMap.SimpleEntry<Method, Object>(m, obj));
        commandMap.put(this.plugin.getName() + ':' + label.toLowerCase(), new AbstractMap.SimpleEntry<Method, Object>(m, obj));
        String cmdLabel = label.split("\\.")[0].toLowerCase();
        if (map.getCommand(cmdLabel) == null) {
            org.bukkit.command.Command cmd = new BukkitCommand(cmdLabel, this, plugin);
            map.register(plugin.getName(), cmd);
        }
        if (!command.description().equalsIgnoreCase("") && cmdLabel.equals(label)) {
            map.getCommand(cmdLabel).setDescription(command.description());
        }
        if (!command.usage().equalsIgnoreCase("") && cmdLabel.equals(label)) {
            map.getCommand(cmdLabel).setUsage(command.usage());
        }
    }

    public void registerCompleter0(String label, Method m, Object obj) {
        String cmdLabel = label.split("\\.")[0].toLowerCase();
        if (map.getCommand(cmdLabel) == null) {
            org.bukkit.command.Command command = new BukkitCommand(cmdLabel, this, plugin);
            map.register(plugin.getName(), command);
        }
        if (map.getCommand(cmdLabel) instanceof BukkitCommand) {
            BukkitCommand command = (BukkitCommand) map.getCommand(cmdLabel);
            if (command.completer == null) {
                command.completer = new BukkitCompleter();
            }
            command.completer.addCompleter(label, m, obj);
        } else if (map.getCommand(cmdLabel) instanceof PluginCommand) {
            try {
                Object command = map.getCommand(cmdLabel);
                Field field = command.getClass().getDeclaredField("completer");
                field.setAccessible(true);
                if (field.get(command) == null) {
                    BukkitCompleter completer = new BukkitCompleter();
                    completer.addCompleter(label, m, obj);
                    field.set(command, completer);
                } else if (field.get(command) instanceof BukkitCompleter) {
                    BukkitCompleter completer = (BukkitCompleter) field.get(command);
                    completer.addCompleter(label, m, obj);
                } else {
                    System.out.println("Unable to register tab completer " + m.getName()
                            + ". A tab completer is already registered for that command!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void defaultCommand(CommandArgs args) {
        args.getSender().sendMessage("§cCette commande n'est pas executable. Utilisez plutôt /" + args.getCommand().getName());
    }

    public Map<HeriaCommand, HeriaRank> getPermissionsCommands() {
        return permissionsCommands;
    }
}
