package fr.heriamc.bukkit.menu.confirm;

import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.menu.HeriaMenu;
import fr.heriamc.bukkit.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.function.Consumer;

public abstract class ConfirmMenu extends HeriaMenu {

    private final String name;
    private final HeriaBukkit bukkit;
    private final HeriaMenu before;
    private final Consumer<Player> confirmAction;

    public ConfirmMenu(Player player, String name, HeriaBukkit bukkit, HeriaMenu before, Consumer<Player> confirmAction) {
        super(player, name, 54, false);
        this.name = name;
        this.bukkit = bukkit;
        this.before = before;
        this.confirmAction = confirmAction;
    }

    @Override
    public void contents(Inventory inventory) {
        this.inventory(inventory);

        this.insertInteractItem(inventory, 21, getConfirmItem().onClick(event -> {
            this.confirmAction.accept(getPlayer());
        }));

        this.insertInteractItem(inventory, 23, getCancelItem().onClick(event -> {
            bukkit.getMenuManager().open(before);
        }));
    }

    public ItemBuilder getConfirmItem(){
        return new ItemBuilder(Material.SLIME_BALL).setName("§aConfirmer");
    }

    public ItemBuilder getCancelItem(){
        return new ItemBuilder(Material.REDSTONE_BLOCK).setName("§cAnnuler");
    }

    public abstract void inventory(Inventory inventory);
}
