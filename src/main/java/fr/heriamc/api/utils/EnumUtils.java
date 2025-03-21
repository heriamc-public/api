package fr.heriamc.api.utils;

public final class EnumUtils {

    public static <V extends Enum<V>> V getNext(V current, V[] possibilities) {
        if (current == null) {
            return possibilities[0];
        }
        int nextOrdinal = (current.ordinal() + 1) % possibilities.length;
        return possibilities[nextOrdinal];
    }

    public static <V extends Enum<V>> V getPrevious(V current, V[] possibilities) {
        if (current == null) {
            return possibilities[possibilities.length - 1];
        }
        int prevOrdinal = (current.ordinal() - 1 + possibilities.length) % possibilities.length;
        return possibilities[prevOrdinal];
    }

}
