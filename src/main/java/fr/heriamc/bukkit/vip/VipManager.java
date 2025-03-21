package fr.heriamc.bukkit.vip;

import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.vip.tag.TagCommand;

public class VipManager {

    private final HeriaBukkit bukkit;

    public VipManager(HeriaBukkit bukkit) {
        this.bukkit = bukkit;

        this.bukkit.getCommandManager().registerCommand(new TagCommand(bukkit));
    }
}
