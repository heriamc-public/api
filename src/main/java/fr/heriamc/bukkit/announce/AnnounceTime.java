package fr.heriamc.bukkit.announce;

import java.util.concurrent.TimeUnit;

public enum AnnounceTime {

    ONE_HOUR(0, TimeUnit.HOURS.toMillis(1), "1 heure"),
    THREE_HOURS(0, TimeUnit.HOURS.toMillis(3), "2 heures"),
    SIX_HOURS(0.50F, TimeUnit.HOURS.toMillis(6), "6 heures"),
    TWELVE_HOURS(1F, TimeUnit.HOURS.toMillis(12), "12 heures"),
    ONE_DAY(1.50F, TimeUnit.HOURS.toMillis(24), "1 jour"),
    TWO_DAYS(2F, TimeUnit.HOURS.toMillis(48), "2 jours");

    private final float price;
    private final long time;
    private final String name;

    AnnounceTime(float price, long time, String name) {
        this.price = price;
        this.time = time;
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public long getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

}
