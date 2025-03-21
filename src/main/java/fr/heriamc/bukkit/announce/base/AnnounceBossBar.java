package fr.heriamc.bukkit.announce.base;

import fr.heriamc.api.user.HeriaPlayer;
import fr.heriamc.bukkit.announce.AnnounceManager;
import fr.heriamc.bukkit.announce.HeriaAnnounce;
import fr.heriamc.bukkit.utils.bossbar.BossBar;
import fr.heriamc.bukkit.utils.bossbar.BossBarManager;
import fr.heriamc.bukkit.utils.bossbar.animation.BossBarAnimation.Evolution;
import fr.heriamc.bukkit.utils.bossbar.animation.BossBarAnimation.Evolution.Options;
import fr.heriamc.bukkit.utils.bossbar.animation.BossBarAnimation.Evolution.Type;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class AnnounceBossBar {

    private Type currentType;
    private int announceIndex;

    private final Player player;
    private final AnnounceManager announceManager;

    public AnnounceBossBar(Player player, AnnounceManager announceManager) {
        this.player = player;
        this.announceManager = announceManager;
    }

    public void init(){
        this.currentType = Type.INCREASING;
        this.announceIndex = 0;

        BossBar bar = BossBarManager.setBar(this.player, "", 0.0f);

        this.nextAnnounce().accept(bar);
    }

    public void remove(){
        BossBarManager.removeBar(player);
        //Bukkit.broadcastMessage("removing bar");
    }

    public Consumer<BossBar> nextAnnounce(){
        //Bukkit.broadcastMessage("nextAnnounce skip");
        return bar -> {
            List<HeriaAnnounce> announces = this.getAnnounces();

            if(announces.size() == 0) {
                this.remove();
                return;
            }

            if(this.announceIndex >= announces.size()){
                this.announceIndex = 0;
            }

            HeriaAnnounce announce = announces.get(this.announceIndex);
            HeriaPlayer owner = announceManager.getBukkit().getApi().getPlayerManager().get(announce.getUser());
            Evolution animation = new Evolution(this.currentType, new Options(5 * 20L, this.nextAnnounce()));

            bar.setText(owner.getRank().getPrefix() + owner.getName() + "§7: §r" + announce.getMessage().replaceAll("&", "§"));
            bar.applyAnimation(animation);

            this.currentType = this.currentType == Type.INCREASING ? Type.DECREASING : Type.INCREASING;
            this.announceIndex++;
        };
    }

    private List<HeriaAnnounce> getAnnounces(){
        CopyOnWriteArrayList<HeriaAnnounce> announces = new CopyOnWriteArrayList<>(announceManager.getAllFromPersistent());
        //Bukkit.broadcastMessage("there is " + announces.size() + " announces");

        for (HeriaAnnounce announce : announces) {
            if(System.currentTimeMillis() > announce.getInstant() + announce.getDuration().getTime()){
                announceManager.removeInPersistant(announce);
                announceManager.remove(announce);
                announces.remove(announce);
            }
        }

        //Bukkit.broadcastMessage("there is " + announces.size() + " announces after expiration check");

        return announces;
    }
}
