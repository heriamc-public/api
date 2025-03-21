package fr.heriamc.api.utils;

public final class EnumIncludeNullUtils {

    public static <V extends Enum<V>> V getNext(V current, V[] possibilities) {
        if (current == null) {
            return possibilities[0];
        }
        int nextOrdinal = current.ordinal() + 1;
        if (nextOrdinal >= possibilities.length) {
            return null;
        }
        return possibilities[nextOrdinal];
    }

    public static <V extends Enum<V>> V getPrevious(V current, V[] possibilities) {
        if (current == null) {
            return possibilities[possibilities.length - 1];
        }
        int prevOrdinal = current.ordinal() - 1;
        if (prevOrdinal < 0) {
            return null;
        }
        return possibilities[prevOrdinal];
    }
}
