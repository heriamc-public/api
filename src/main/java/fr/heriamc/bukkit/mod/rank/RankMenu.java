package fr.heriamc.bukkit.mod.rank;

import fr.heriamc.api.user.HeriaPlayer;
import fr.heriamc.api.user.rank.HeriaRank;
import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.menu.HeriaMenu;
import fr.heriamc.bukkit.utils.ItemBuilder;
import fr.heriamc.proxy.packet.model.ProxyPlayerMessagePacket;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class RankMenu extends HeriaMenu {

    private final HeriaBukkit heriaBukkit;
    private final HeriaPlayer target;

    private final int[] slots = new int[]{
            10,11,12,13,14,15,16,
            19,20,21,22,23,24,25,
            28,29,30,31,32,33,34,
            37,38,39,40,41,42,43};

    public RankMenu(Player player, HeriaBukkit heriaBukkit, HeriaPlayer target) {
        super(player, "Grade de " + target.getName(), 54, false);
        this.heriaBukkit = heriaBukkit;
        this.target = target;
    }

    @Override
    public void contents(Inventory inv) {
        setBorder(inv, DyeColor.RED.getWoolData());

        int index = 0;
        for (HeriaRank value : HeriaRank.values()) {
            this.insertInteractItem(inv, slots[index], new ItemBuilder(Material.WOOL).setWoolColor(DyeColor.getByWoolData((byte) value.getColor().getWoolData()))
                    .setName(value.getColor() + value.getName())
                    .addLore(" ")
                        .addLore("§6§l» §eClique: §fAttribuer ce role")
                    .onClick(event -> {
                        getPlayer().closeInventory();

                        target.setRank(value);
                        heriaBukkit.getApi().getPlayerManager().save(target);
                        heriaBukkit.getApi().getPlayerManager().saveInPersistant(target);
                        getPlayer().sendMessage("§aVous avez mis le rôle " + value.getName() + " à " + target.getName());
                        heriaBukkit.getApi().getMessaging().send(new ProxyPlayerMessagePacket(target.getId(),
                                "§aVous avez reçu le grade " + value.getName() + " de la part de " + getPlayer().getName()));

                    }));

            index++;
        }
    }
}
