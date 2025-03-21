package fr.heriamc.api.utils;

import fr.heriamc.bukkit.utils.TimeUnit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public final class TimeUtils {

    public static String getTimeToString(Long millisecondes) {
        long diff1 = millisecondes.longValue() / 1000L;
        String diff2 = String.valueOf(diff1);
        String diff3 = diff2.replaceAll("-", "");
        long diff = Long.valueOf(diff3).longValue();
        int heure = (int) (diff / 60L / 60L);
        int minutes = (int) (diff / 60L % 60L);
        int secondes = (int) (diff % 60L);
        String heureStr = String.valueOf(heure);
        String minutesStr = String.valueOf(minutes);
        String secondesStr = String.valueOf(secondes);
        if (heure < 10)
            heureStr = "0" + heureStr;
        if (minutes < 10)
            minutesStr = "0" + minutesStr;
        if (secondes < 10)
            secondesStr = "0" + secondesStr;
        if (diff > 3600L)
            return heureStr + ":" + minutesStr + ":" + secondesStr;
        if (diff > 60L)
            return "00:" + minutesStr + ":" + secondesStr;
        return "00:00:" + secondesStr;
    }

    public static String formatDurationMillisToString(long millis) {
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        seconds %= 60;
        minutes %= 60;

        if (hours > 0) {
            return String.format("%dh%02dm%02ds", hours, minutes, seconds);
        } else if (minutes > 0) {
            return String.format("%dm%02ds", minutes, seconds);
        } else {
            return String.format("%ds", seconds);
        }
    }

    public static String getStringTimeRestant(Long millisecondes) {
        long diff1 = millisecondes.longValue() / 1000L;
        String diff2 = String.valueOf(diff1);
        String diff3 = diff2.replaceAll("-", "");
        long diff = Long.valueOf(diff3).longValue();
        if (diff > 86400L)
            return (diff / 60L / 60L / 24L) + "j " + (diff / 60L / 60L % 24L) + "h " + (diff / 60L % 60L) + "m";
        if (diff > 3600L)
            return (diff / 60L / 60L) + "h " + (diff / 60L % 60L) + "m";
        if (diff > 60L)
            return (diff / 60L) + "m ";
        return diff + "s";
    }

    public static String getTimeRestant(Long millisecondes) {
        long diff1 = millisecondes.longValue() / 1000L;
        String diff2 = String.valueOf(diff1);
        String diff3 = diff2.replaceAll("-", "");
        long diff = Long.valueOf(diff3).longValue();
        if (diff > 86400L)
            return (diff / 60L / 60L / 24L) + " jours " + (diff / 60L / 60L % 24L) + " heures " + (diff / 60L % 60L) + " minutes " + (diff % 60L) + " secondes ";
        if (diff > 3600L)
            return (diff / 60L / 60L) + " heures " + (diff / 60L % 60L) + " minutes " + (diff % 60L) + " secondes ";
        if (diff > 60L)
            return (diff / 60L) + " minutes " + (diff % 60L) + " secondes";
        return diff + " secondes ";
    }

    public static String convertMilliSecondsToFormattedDate(Long milliSeconds) {
        String dateFormat = "dd/MM/yyyy hh:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return simpleDateFormat.format(calendar.getTime());
    }


    public static String getCurrentDate() {
        Date date = new Date();
        DateFormat format = DateFormat.getDateTimeInstance(3, 3, new Locale("FR", "fr"));
        return format.format(date);
    }

    public static String transformLongToFormatedDate(long time) {
        long tempsRestant = (time - System.currentTimeMillis()) / 1000L;
        int mois = 0;
        int jours = 0;
        int heures = 0;
        int minutes = 0;
        int secondes = 0;
        while (tempsRestant >= TimeUnit.MOIS.getToSecond()) {
            mois++;
            tempsRestant -= TimeUnit.MOIS.getToSecond();
        }
        while (tempsRestant >= TimeUnit.JOUR.getToSecond()) {
            jours++;
            tempsRestant -= TimeUnit.JOUR.getToSecond();
        }
        while (tempsRestant >= TimeUnit.HEURE.getToSecond()) {
            heures++;
            tempsRestant -= TimeUnit.HEURE.getToSecond();
        }
        while (tempsRestant >= TimeUnit.MINUTE.getToSecond()) {
            minutes++;
            tempsRestant -= TimeUnit.MINUTE.getToSecond();
        }
        while (tempsRestant >= TimeUnit.SECONDE.getToSecond()) {
            secondes++;
            tempsRestant -= TimeUnit.SECONDE.getToSecond();
        }
        String formattedTime = "";
        if (mois > 0) {
            formattedTime += mois + " " + TimeUnit.MOIS.getName() + ", ";
        }
        if (jours > 0) {
            formattedTime += jours + " " + TimeUnit.JOUR.getName() + ", ";
        }
        if (heures > 0) {
            formattedTime += heures + " " + TimeUnit.HEURE.getName() + ", ";
        }
        if (minutes > 0) {
            formattedTime += minutes + " " + TimeUnit.MINUTE.getName() + ", ";
        }
        if (secondes > 0) {
            formattedTime += secondes + " " + TimeUnit.SECONDE.getName();
        }
        // Enlever la virgule et l'espace à la fin du texte si nécessaire
        if (formattedTime.endsWith(", ")) {
            formattedTime = formattedTime.substring(0, formattedTime.length() - 2);
        }
        return formattedTime;
    }

}