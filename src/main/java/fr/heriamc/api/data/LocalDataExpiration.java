package fr.heriamc.api.data;

import java.util.concurrent.TimeUnit;

public class LocalDataExpiration {

    private final long duration;
    private final TimeUnit unit;

    public LocalDataExpiration(long duration, TimeUnit unit) {
        this.duration = duration;
        this.unit = unit;
    }

    public long duration() {
        return duration;
    }

    public TimeUnit unit() {
        return unit;
    }

}
