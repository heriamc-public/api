package fr.heriamc.api.user.rank;


import fr.heriamc.api.utils.HeriaChatColor;

public enum HeriaRank {

    OWNER("Gérant", HeriaChatColor.DARK_RED, 100, 1),
    ADMIN("Admin", HeriaChatColor.RED, 90, 2),
    RESP_PERM("Responsable §7(Avec permissions)", HeriaChatColor.RED, 80, 3),
    RESPONSABLE("Responsable", HeriaChatColor.RED, 40, 3),
    SUPER_MOD("SuperModo", HeriaChatColor.RED, 70, 4),
    MOD("Modérateur", HeriaChatColor.BLUE, 60, 5),
    HELPER("Assistant", HeriaChatColor.AQUA, 50, 6),
    DEV("Développeur", HeriaChatColor.DARK_GREEN, 1000, 7),
    BUILD("Builder", HeriaChatColor.GREEN, 40, 8),
    GRAPHIC("Graphiste", HeriaChatColor.DARK_GREEN, 40, 9),

    TIKTOKER("Tiktoker", HeriaChatColor.WHITE, 10, 10),
    YOUTUBER("Youtuber", HeriaChatColor.GOLD, 10, 11),
    STREAMER("Streamer", HeriaChatColor.DARK_PURPLE, 10, 12),
    CUSTOM("Perso", HeriaChatColor.WHITE, 6, 13),
    SUPREME("Suprême", HeriaChatColor.LIGHT_PURPLE, 5, 14),
    SUPER_VIP("SuperVIP", HeriaChatColor.AQUA, 4, 15),
    VIP_PLUS("VIP+", HeriaChatColor.DARK_AQUA, 3, 16),
    VIP("VIP", HeriaChatColor.YELLOW, 2, 17),
    PLAYER("Joueur", HeriaChatColor.GRAY, 1, 18),

    ;

    public final static HeriaRank DEFAULT = PLAYER;

    private final String name;
    private final HeriaChatColor color;
    private final int power;
    private final int tabPriority;

    HeriaRank(String name, HeriaChatColor color, int power, int tabPriority) {
        this.name = name;
        this.color = color;
        this.power = power;
        this.tabPriority = tabPriority;
    }

    public String getName() {
        return name;
    }

    public HeriaChatColor getColor() {
        return color;
    }

    public String getPrefix(){
        if(this == RESP_PERM) return RESPONSABLE.getPrefix();
        return getColor() + getName() + " " + getColor();
    }

    public int getPower() {
        return power;
    }

    public int getTabPriority() {
        return tabPriority;
    }

}
