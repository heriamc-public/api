package fr.heriamc.bukkit.mod.sanction;

import fr.heriamc.api.sanction.HeriaSanctionType;
import org.bukkit.Material;

import java.util.concurrent.TimeUnit;

public enum UISanctionSubType {

    FAKE_INFO(UISanctionType.CHAT, TimeUnit.MINUTES.toSeconds(5), "Fausses Informations", Material.SUGAR_CANE),
    FLOOD(UISanctionType.CHAT, TimeUnit.MINUTES.toSeconds(10), "Flood/Spam", Material.ANVIL),
    VANTARDISE(UISanctionType.CHAT, TimeUnit.MINUTES.toSeconds(10), "Vantardise", Material.WEB),
    PROVOCATIONS(UISanctionType.CHAT, TimeUnit.MINUTES.toSeconds(15), "Provocations", Material.DEAD_BUSH),
    BAD_LANGUAGE(UISanctionType.CHAT, TimeUnit.MINUTES.toSeconds(30), "Mauvais language", Material.TNT),
    INSULTES(UISanctionType.CHAT, TimeUnit.HOURS.toSeconds(1), "Insultes", Material.WATER_LILY),
    MENDICITY(UISanctionType.CHAT, TimeUnit.HOURS.toSeconds(3), "Mendicité", Material.TRIPWIRE_HOOK),
    PROMOTION_WITHOUT_IP(UISanctionType.CHAT, TimeUnit.DAYS.toSeconds(3), "Publicité sans IP", Material.HOPPER),

    BAD_SKIN(UISanctionType.GAMEPLAY, TimeUnit.DAYS.toSeconds(2), "Skin inapproprié", Material.SKULL_ITEM),
    FORBIDDEN_LINK(UISanctionType.CHAT, TimeUnit.DAYS.toSeconds(3), "Liens interdits", Material.FLINT, HeriaSanctionType.BAN),
    PROMOTION_WITH_IP(UISanctionType.CHAT, TimeUnit.DAYS.toSeconds(14), "Publicité avec IP", Material.APPLE, HeriaSanctionType.BAN),
    BAD_NAME(UISanctionType.GAMEPLAY, TimeUnit.DAYS.toSeconds(30), "Pseudo Inapproprié", Material.NAME_TAG),
    DDOS(UISanctionType.CHAT, TimeUnit.DAYS.toSeconds(60), "Menace de DDOS", Material.BLAZE_POWDER, HeriaSanctionType.BAN),
    PERSONAL_INFO(UISanctionType.CHAT, TimeUnit.DAYS.toSeconds(60), "Divulguation d'Informations Personnelles", Material.STRING, HeriaSanctionType.BAN),
    RACISM(UISanctionType.CHAT, TimeUnit.DAYS.toSeconds(7), "Racisme/Sexisme/Homophobie", Material.SIGN, HeriaSanctionType.BAN),

    ANTI_KB(UISanctionType.CHEAT, TimeUnit.DAYS.toSeconds(30), "Anti-Knockback", Material.LEATHER),
    KILLAURA(UISanctionType.CHEAT, TimeUnit.DAYS.toSeconds(30), "KillAura/ForceField", Material.RAW_FISH),
    FLY(UISanctionType.CHEAT, TimeUnit.DAYS.toSeconds(30), "Fly", Material.FEATHER),
    REACH(UISanctionType.CHEAT, TimeUnit.DAYS.toSeconds(30), "Reach", Material.FISHING_ROD),
    LITTLE_MARCO(UISanctionType.CHEAT, TimeUnit.DAYS.toSeconds(15), "Petite Macro (+25CPS)", Material.GLOWSTONE_DUST),
    BIG_MARCO(UISanctionType.CHEAT, TimeUnit.DAYS.toSeconds(30), "Grosse Macro (+30CPS)", Material.REDSTONE),
    OTHER(UISanctionType.CHEAT, TimeUnit.DAYS.toSeconds(30), "Autres", Material.EGG),

    INCORRECT_BUILD(UISanctionType.GAMEPLAY, TimeUnit.DAYS.toSeconds(7), "Construction Incorrecte", Material.WOOD_DOOR),
    TEAMING_SOLO(UISanctionType.GAMEPLAY, TimeUnit.DAYS.toSeconds(3), "Alliance en Jeu Solo", Material.SHEARS),
    TEAMING_TEAM(UISanctionType.GAMEPLAY, TimeUnit.DAYS.toSeconds(3), "Alliance entre Equipe", Material.RAW_CHICKEN),
    SPAWN_KILL(UISanctionType.GAMEPLAY, TimeUnit.DAYS.toSeconds(1), "SpawnKill", Material.DIAMOND_SWORD),
    BAD_ZONE_NAME(UISanctionType.GAMEPLAY, TimeUnit.DAYS.toSeconds(3), "Nom de zone Incorrect", Material.NAME_TAG),
    SANCTION_ESCAPE(UISanctionType.GAMEPLAY, TimeUnit.DAYS.toSeconds(7), "Contournement de Sanction", Material.ENDER_PEARL),

    ABUS(UISanctionType.GAMEPLAY, TimeUnit.DAYS.toSeconds(1), "Abus de report", Material.REDSTONE_BLOCK, HeriaSanctionType.WARN)

    ;

    private final UISanctionType type;
    private final long duration;
    private final String banReason;
    private final Material material;
    private final HeriaSanctionType sanctionType;

    UISanctionSubType(UISanctionType type, long duration, String banReason, Material material) {
        this(type, duration, banReason, material, type.getSanctionType());
    }

    UISanctionSubType(UISanctionType type, long duration, String banReason, Material material, HeriaSanctionType sanctionType) {
        this.type = type;
        this.duration = duration;
        this.banReason = banReason;
        this.material = material;
        this.sanctionType = sanctionType;
    }

    public UISanctionType getType() {
        return type;
    }

    public long getDuration() {
        return duration;
    }

    public Material getMaterial() {
        return material;
    }

    public String getBanReason() {
        return banReason + " (" + type.getBanReason() + ")";
    }

    public String getInternBanReason(){
        return banReason;
    }

    public HeriaSanctionType getSanctionType() {
        return sanctionType;
    }
}
