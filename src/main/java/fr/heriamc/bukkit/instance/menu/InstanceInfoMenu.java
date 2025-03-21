package fr.heriamc.bukkit.instance.menu;

import fr.heriamc.api.server.HeriaServer;
import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.menu.HeriaMenu;
import fr.heriamc.bukkit.utils.ItemBuilder;
import fr.heriamc.proxy.packet.model.SendPlayerPacket;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InstanceInfoMenu extends HeriaMenu {

    private final HeriaBukkit bukkit;
    private final HeriaMenu before;
    private final HeriaServer heriaServer;

    public InstanceInfoMenu(Player player, HeriaBukkit bukkit, HeriaMenu before, HeriaServer heriaServer) {
        super(player, "Gestion de " + heriaServer.getName(), 54, true);
        this.bukkit = bukkit;
        this.before = before;
        this.heriaServer = heriaServer;
    }

    @Override
    public void contents(Inventory inv) {
        inv.setItem(0, new ItemBuilder(Material.NAME_TAG).setName(heriaServer.getName()).build());
        this.insertInteractItem(inv, 1, new ItemBuilder(Material.SLIME_BLOCK).setName("§aRejoindre").onClick(event -> {
            this.bukkit.getApi().getMessaging().send(new SendPlayerPacket(getPlayer().getUniqueId(), heriaServer.getName()));
        }));

        this.insertInteractItem(inv, 2, new ItemBuilder(Material.DARK_OAK_DOOR_ITEM).onClick(event -> {
            bukkit.getMenuManager().open(before);
        }));
    }
}
