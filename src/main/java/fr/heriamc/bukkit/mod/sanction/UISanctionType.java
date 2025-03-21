package fr.heriamc.bukkit.mod.sanction;

import fr.heriamc.api.sanction.HeriaSanctionType;
import fr.heriamc.bukkit.utils.ItemBuilder;
import org.bukkit.Material;

public enum UISanctionType {

    CHEAT(HeriaSanctionType.BAN, "Cheat", 21, new ItemBuilder(Material.IRON_SWORD).setName("§bTriche")),
    CHAT(HeriaSanctionType.MUTE, "Chat", 22, new ItemBuilder(Material.COMPASS).setName("§aChat")),
    GAMEPLAY(HeriaSanctionType.BAN, "Gameplay", 23, new ItemBuilder(Material.BED).setName("§eGameplay"))

    ;

    private final HeriaSanctionType sanctionType;
    private final String banReason;
    private final int slot;
    private final ItemBuilder item;

    UISanctionType(HeriaSanctionType sanctionType, String banReason, int slot, ItemBuilder item) {
        this.sanctionType = sanctionType;
        this.banReason = banReason;
        this.slot = slot;
        this.item = item;
    }

    public HeriaSanctionType getSanctionType() {
        return sanctionType;
    }

    public String getBanReason() {
        return banReason;
    }

    public int getSlot() {
        return slot;
    }

    public ItemBuilder getItem() {
        return item;
    }
}
