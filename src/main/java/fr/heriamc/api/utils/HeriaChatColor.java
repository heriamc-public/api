package fr.heriamc.api.utils;

public enum HeriaChatColor {

    BLACK('0', 15),
    DARK_BLUE('1', 11),
    DARK_GREEN('2', 13),
    DARK_AQUA('3', 9),
    DARK_RED('4', 14),
    DARK_PURPLE('5', 10),
    GOLD('6', 1),
    GRAY('7', 8),
    DARK_GRAY('8', 7),
    BLUE('9', 3),
    GREEN('a', 5),
    AQUA('b', 3),
    RED('c', 14),
    LIGHT_PURPLE('d', 6),
    YELLOW('e', 4),
    WHITE('f', 0),
    MAGIC('k', 0),
    BOLD('l', 0),
    STRIKETHROUGH('m', 0),
    UNDERLINE('n', 0),
    ITALIC('o', 0),
    RESET('r', 0)

    ;

    private final static String CONVERTOR = "§";
    private final char plain;
    private final int woolData;

    HeriaChatColor(char plain, int woolData) {
        this.plain = plain;
        this.woolData = woolData;
    }

    public String getColor(){
        return CONVERTOR + this.plain;
    }

    @Override
    public String toString() {
        return getColor();
    }

    public int getWoolData() {
        return woolData;
    }
}
