package fr.heriamc.bukkit.announce.base;

import fr.heriamc.api.user.HeriaPlayer;
import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.announce.AnnounceTime;
import fr.heriamc.bukkit.announce.HeriaAnnounce;
import fr.heriamc.bukkit.menu.HeriaMenu;
import fr.heriamc.bukkit.utils.ItemBuilder;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class AnnounceMenu extends HeriaMenu {

    private final HeriaBukkit bukkit;
    private final String message;

    public AnnounceMenu(Player player, HeriaBukkit bukkit, String message) {
        super(player, "Annonce", 54, false);
        this.bukkit = bukkit;
        this.message = message;
    }

    @Override
    public void contents(Inventory inv) {
        this.setBorder(inv, DyeColor.MAGENTA.getData());

        int index = 20;
        for (AnnounceTime value : AnnounceTime.values()) {
            this.insertInteractItem(inv, index, new ItemBuilder(Material.EXP_BOTTLE)
                    .setName("§a" + value.getName())
                    .addLore(" ")
                    .addLore("§7Faites diffuser un message sur")
                    .addLore("§7tout HeriaMC pendant " + value.getName())
                    .addLore(" ")
                    .addLore("§8» §7Prix: §d" + value.getPrice() + " EUR")
                    .addLore("§8» §7Durée: §6" + value.getName())
                    .addLore(" ")
                    .addLore("§6§l» §eClique: §fAccepter")
                    .onClick(event -> {
                        HeriaPlayer heriaPlayer = bukkit.getApi().getPlayerManager().get(getPlayer().getUniqueId());

                        if(heriaPlayer.getCredits() < value.getPrice()){
                            getPlayer().sendMessage("§cVous n'avez pas les fonds nécessaires");
                            getPlayer().closeInventory();
                            return;
                        }

                        HeriaAnnounce heriaAnnounce = bukkit.getAnnounceManager().createOrLoad(UUID.randomUUID());
                        heriaAnnounce.setDuration(value);
                        heriaAnnounce.setUser(getPlayer().getUniqueId());
                        heriaAnnounce.setMessage(this.message);

                        getPlayer().closeInventory();

                        getPlayer().sendMessage("§aVotre annonce a bien été publiée ! Elle va apparaitre dans quelques instants dans la barre de boss de tout le serveur.");

                        bukkit.getAnnounceManager().save(heriaAnnounce);
                        bukkit.getAnnounceManager().saveInPersistant(heriaAnnounce);
                    }));

            index++;
        }
    }
}
