package fr.heriamc.bukkit.utils;

import java.util.HashMap;

public enum TimeUnit {
    SECONDE("Seconde(s)", "s", 1L),
    MINUTE("Minute(s)", "m", 60L),
    HEURE("Heure(s)", "h", 3600L),
    JOUR("Jour(s)", "d", 86400L),
    MOIS("Mois", "m", 2592000L),
    ANNEE("Année(s)", "y", 31536000L);

    private final String name;

    private final String shortcut;

    private final long toSecond;

    private static final HashMap<String, TimeUnit> id_shortcuts;

    static {
        id_shortcuts = new HashMap<>();
        for (TimeUnit units : values())
            id_shortcuts.put(units.shortcut, units);
    }

    TimeUnit(String name, String shortcut, long toSecond) {
        this.name = name;
        this.shortcut = shortcut;
        this.toSecond = toSecond;
    }

    public static TimeUnit getFromShortcut(String shortcut) {
        return id_shortcuts.get(shortcut);
    }

    public static long getTimeToSecond(String args) {
        int duration;
        try {
            duration = Integer.parseInt(args.split(":")[0]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0L;
        }
        TimeUnit unit = getFromShortcut(args.split(":")[1]);
        return unit.getToSecond() * duration;
    }

    public static int getYearFromLong(long time) {
        long timeLeft = time / 1000L;
        int year = 0;
        while (timeLeft >= ANNEE.getToSecond()) {
            year++;
            timeLeft -= ANNEE.getToSecond();
        }
        return year;
    }

    public static int getMonthFromLong(long time) {
        long timeLeft = time / 1000L;
        int month = 0;
        while (timeLeft >= MOIS.getToSecond()) {
            month++;
            timeLeft -= MOIS.getToSecond();
        }
        return month;
    }

    public static int getDayFromLong(long time) {
        long timeLeft = time / 1000L;
        int day = 0;
        while (timeLeft >= JOUR.getToSecond()) {
            day++;
            timeLeft -= JOUR.getToSecond();
        }
        return day;
    }

    public static int getHourFromLong(long time) {
        long timeLeft = time / 1000L;
        int hour = 0;
        while (timeLeft >= HEURE.getToSecond()) {
            hour++;
            timeLeft -= HEURE.getToSecond();
        }
        return hour;
    }

    public static int getMinuteFromLong(long time) {
        long timeLeft = time / 1000L;
        int minute = 0;
        while (timeLeft >= MINUTE.getToSecond()) {
            minute++;
            timeLeft -= MINUTE.getToSecond();
        }
        return minute;
    }

    public static int getSecondFromLong(long time) {
        long timeLeft = time / 1000L;
        int second = 0;
        while (timeLeft >= SECONDE.getToSecond()) {
            second++;
            timeLeft -= SECONDE.getToSecond();
        }
        return second;
    }

    public static String getTimeFromLong(long time) {
        long tempsRestant = time / 1000L;
        int annee = 0;
        int mois = 0;
        int jours = 0;
        int heures = 0;
        int minutes = 0;
        int secondes = 0;
        while (tempsRestant >= ANNEE.getToSecond()) {
            annee++;
            tempsRestant -= ANNEE.getToSecond();
        }
        while (tempsRestant >= MOIS.getToSecond()) {
            mois++;
            tempsRestant -= MOIS.getToSecond();
        }
        while (tempsRestant >= JOUR.getToSecond()) {
            jours++;
            tempsRestant -= JOUR.getToSecond();
        }
        while (tempsRestant >= HEURE.getToSecond()) {
            heures++;
            tempsRestant -= HEURE.getToSecond();
        }
        while (tempsRestant >= MINUTE.getToSecond()) {
            minutes++;
            tempsRestant -= MINUTE.getToSecond();
        }
        while (tempsRestant >= SECONDE.getToSecond()) {
            secondes++;
            tempsRestant -= SECONDE.getToSecond();
        }
        return annee + " " + ANNEE.getName() + ", " + mois + " " + MOIS.getName() + ", " + jours + " " + JOUR.getName() + ", " + heures + " " + HEURE.getName() + ", " + minutes + " " + MINUTE.getName() + ", " + secondes + " " + SECONDE.getName();
    }

    public static Long parseTimeStringToSeconds(String timeString) {
        long totalSeconds = 0L;
        StringBuilder numberBuffer = new StringBuilder();

        try {
            for (int i = 0; i < timeString.length(); i++) {
                char currentChar = timeString.charAt(i);

                if (Character.isDigit(currentChar)) {
                    numberBuffer.append(currentChar);
                } else {
                    String unitShortcut = String.valueOf(currentChar);
                    int duration = Integer.parseInt(numberBuffer.toString());
                    TimeUnit unit = TimeUnit.getFromShortcut(unitShortcut);

                    if (unit != null) {
                        totalSeconds += duration * unit.getToSecond();
                    } else {
                        throw new IllegalArgumentException("Unité de temps inconnue: " + unitShortcut);
                    }

                    numberBuffer.setLength(0);
                }
            }

            return totalSeconds;
        } catch (Exception e) {
            return null;
        }
    }

    public String getName() {
        return this.name;
    }

    public String getShortcut() {
        return this.shortcut;
    }

    public long getToSecond() {
        return this.toSecond;
    }

    public static boolean existFromShortcut(String shortcut) {
        return id_shortcuts.containsKey(shortcut);
    }
}