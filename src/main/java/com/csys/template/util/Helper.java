/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csys.template.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Farouk
 */
public class Helper {

    public static byte[] read(ByteArrayInputStream bais) throws IOException {
        byte[] array = new byte[bais.available()];
        bais.read(array);

        return array;
    }

    public static String incrementString(String suffix) {

        Integer suffixint = Integer.valueOf(suffix);
        suffixint++;
        String format = "%0" + suffix.length() + "d";
        String newsuffix = String.format(format, suffixint);
        return newsuffix;
    }

    public static String generatePrefixAndSuffix(Integer codeLength, String prefix) {

        int taillesuffix = codeLength - prefix.length();
        String format = "%0" + taillesuffix + "d";
        String suffix = String.format(format, 1); // returns ..001
        return suffix;
    }

    public static String findNameEnglishDayByDate(Date date) {
        String name = "";
        Integer day = date.getDay();

        switch (day) {
            case 1:
                name = "Monday";

                break;
            case 2:
                name = "Tuesday";
                break;
            case 3:
                name = "Wednesday";
                break;
            case 4:
                name = "Thursday";
                break;
            case 5:
                name = "Friday";
                break;
            case 6:
                name = "Saturday";
                break;
            case 0:
                name = "Sunday";
                break;
            default:
                break;
        }
        return name;
    }

    public static String findNameArabeDayByDate(Date date) {
        String name = "";
        Integer day = date.getDay();

        switch (day) {
            case 1:
                name = "الإثنين";

                break;
            case 2:
                name = "الثلاثاء";
                break;
            case 3:
                name = "الأربعاء";
                break;
            case 4:
                name = "الخميس";
                break;
            case 5:
                name = "الجمعة";
                break;
            case 6:
                name = "السبت";
                break;
            case 0:
                name = "الأحد";
                break;
            default:
                break;
        }
        return name;
    }

    public static String getMimeType(String extension) {
        String mime = "";
        switch (extension) {
            case "doc":
                mime = "data:application/msword;base64,";
                break;
            case "dot":
                mime = "data:application/msword;base64,";
                break;

            case "docx":
                mime = "data:application/vnd.openxmlformats-officedocument.wordprocessingml.document;base64,";
                break;
            case "dotx":
                mime = "data:application/vnd.openxmlformats-officedocument.wordprocessingml.template;base64,";
                break;
            case "docm":
                mime = "data:application/vnd.ms-word.document.macroEnabled.12;base64,";
                break;
            case "dotm":
                mime = "data:application/vnd.ms-word.template.macroEnabled.12;base64,";
                break;

            case "xls":
                mime = "data:application/vnd.ms-excel;base64,";
                break;
            case "xlt":
                mime = "data:application/vnd.ms-excel;base64,";
                break;
            case "xla":
                mime = "data:application/vnd.ms-excel;base64,";
                break;

            case "xlsx":
                mime = "data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;base64,";
                break;
            case "xltx":
                mime = "data:application/vnd.openxmlformats-officedocument.spreadsheetml.template;base64,";
                break;
            case "xlsm":
                mime = "data:application/vnd.ms-excel.sheet.macroEnabled.12;base64,";
                break;
            case "xltm":
                mime = "data:application/vnd.ms-excel.template.macroEnabled.12;base64,";
                break;
            case "xlam":
                mime = "data:application/vnd.ms-excel.addin.macroEnabled.12;base64,";
                break;
            case "xlsb":
                mime = "data:application/vnd.ms-excel.sheet.binary.macroEnabled.12;base64,";
                break;

            case "ppt":
                mime = "data:application/vnd.ms-powerpoint;base64,";
                break;
            case "pot":
                mime = "data:application/vnd.ms-powerpoint;base64,";
                break;
            case "pps":
                mime = "data:application/vnd.ms-powerpoint;base64,";
                break;
            case "ppa":
                mime = "data:application/vnd.ms-powerpoint;base64,";
                break;

            case "pptx":
                mime = "data:application/vnd.openxmlformats-officedocument.presentationml.presentation;base64,";
                break;
            case "potx":
                mime = "data:application/vnd.openxmlformats-officedocument.presentationml.template;base64,";
                break;
            case "ppsx":
                mime = "data:application/vnd.openxmlformats-officedocument.presentationml.slideshow;base64,";
                break;
            case "ppam":
                mime = "data:application/vnd.ms-powerpoint.addin.macroEnabled.12;base64,";
                break;
            case "pptm":
                mime = "data:application/vnd.ms-powerpoint.presentation.macroEnabled.12;base64,";
                break;
            case "potm":
                mime = "data:application/vnd.ms-powerpoint.template.macroEnabled.12;base64,";
                break;
            case "ppsm":
                mime = "data:application/vnd.ms-powerpoint.slideshow.macroEnabled.12;base64,";
                break;

            case "mdb":
                mime = "data:application/vnd.ms-access;base64,";
                break;

            case "txt":
                mime = "data:text/plain;base64,";
                break;

            case "jpg":
                mime = "data:image/" + extension + ";base64,";
                break;

            case "png":
                mime = "data:image/" + extension + ";base64,";
                break;

            case "jpeg":
                mime = "data:image/" + extension + ";base64,";
                break;

            case "gif":
                mime = "data:image/" + extension + ";base64,";
                break;

            case "bmp":
                mime = "data:image/" + extension + ";base64,";
                break;

            default:
                mime = "data:application/" + extension + ";base64,";
        }
        return mime;
    }

    public static Date incrementDate(Date date, int nbrOfDay) {
        Date dt = date;
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, nbrOfDay);
        dt = c.getTime();
        return dt;
    }

    public static Date incrementMonthAndSetFirstDay(Date date, int number) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, number); // 11 = december
        cal.set(Calendar.DAY_OF_MONTH, 1); // new years eve
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static int getWeeksBetween(Date a, Date b) {
        Date aa;
        Date bb;
        if (b.before(a)) {
            return -getWeeksBetween(b, a);
        }
        aa = resetTime(a);
        bb = resetTime(b);

        Calendar cal = new GregorianCalendar();
        cal.setTime(aa);
        int weeks = 0;
        while (cal.getTime().before(bb)) {
            // add another week
            cal.add(Calendar.WEEK_OF_YEAR, 1);
            weeks++;
        }
        return weeks;
    }

    public static Date resetTime(Date d) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date resetTimeToHourOne(Date d) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY, 1);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getDateForPlaningCabinet(Date d) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(d);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.YEAR, 1900);
        return cal.getTime();
    }

    public static Date getLastHourInDate(Date d) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    public static Long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static Long getDifferenceHours(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static Long getDifferenceMinutes(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static Long MinutesToSeconds(Long minutes) {
        long seconds = TimeUnit.MINUTES.toSeconds(minutes);
        return seconds;
    }

    public static Integer getCurrentYear() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    public static Date getFirstDateOfCurrentYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, getCurrentYear());
        cal.set(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getFirstDateOfYear(Integer year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getLastDateOfCurrentYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, getCurrentYear());
        cal.set(Calendar.MONTH, 11); // 11 = december
        cal.set(Calendar.DAY_OF_MONTH, 31); // new years eve
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    public static Date getLastDateOfYear(Integer year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, 11); // 11 = december
        cal.set(Calendar.DAY_OF_MONTH, 31); // new years eve
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    public static String namesLookLike(String str) {
        return str.replace(" ", "").replace(".", "").replace(",", "").replace(":", "").replace("''", "")
                .replace("،", "").replace("{", "").replace("}", "").replace("[", "").replace("]", "")
                .replace("=", "").replace("+", "").replace("_", "").replace("-", "").replace("(", "")
                .replace(")", "").replace("&", "").replace("*", "").replace("^", "").replace("%", "")
                .replace("$", "").replace("#", "").replace("@", "").replace("!", "").replace("÷", "")
                .replace("‘", "").replace("؛", "").replace("×", "").replace("<", "").replace(">", "")
                .replace("إ", "ا").replace("لإ", "ل").replace("\"", "").replace("/", "").replace("،", "")
                .replace("ـ", "").replace("أ", "ا").replace("لأ", "ل").replace("~", "").replace("لآ", "ل")
                .replace("آ", "ا").replace("’", "").replace(",", "").replace("؟", "").replace("ئ", "ا")
                .replace("ء", "ا").replace("ؤ", "ا").replace("لا", "ل").replace("ي", "ى").replace("ة", "ه")
                .replace("و", "ا").replace("ض", "ظ").replace("ث", "ت").replace("س", "ش").replace("اا", "ا")
                .replace("ى", "ا");
    }

    public static boolean isStringContainsLatinCharactersOnly(final String iStringToCheck) {
        return iStringToCheck.matches("^[a-zA-Z0-9. ]+$");
    }

    public static byte[] extractBytes(String ImageName, String extension) throws IOException {
        ByteArrayOutputStream baos;
        BufferedImage originalImage = ImageIO.read(new File(ImageName));
        baos = new ByteArrayOutputStream();
        ImageIO.write(originalImage, extension, baos);
        baos.flush();
        return baos.toByteArray();
    }

    public static String getUserAuthenticated() {
        String user;
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            user = SecurityContextHolder.getContext().getAuthentication().getName();
        } else {
            user = "anonymousUser";
        }
        return user;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @Transactional(
            readOnly = true
    )
    public static String diffDate(LocalDateTime dateDebut, LocalDateTime dateFin) {
        LocalDateTime tempDateTime = LocalDateTime.from(dateFin);

        long years = tempDateTime.until(dateDebut, ChronoUnit.YEARS);
        tempDateTime = tempDateTime.plusYears(years);

        long months = tempDateTime.until(dateDebut, ChronoUnit.MONTHS);
        tempDateTime = tempDateTime.plusMonths(months);

        long days = tempDateTime.until(dateDebut, ChronoUnit.DAYS);
        tempDateTime = tempDateTime.plusDays(days);

        long hours = tempDateTime.until(dateDebut, ChronoUnit.HOURS);
        tempDateTime = tempDateTime.plusHours(hours);

        long minutes = tempDateTime.until(dateDebut, ChronoUnit.MINUTES);
        tempDateTime = tempDateTime.plusMinutes(minutes);

        long seconds = tempDateTime.until(dateDebut, ChronoUnit.SECONDS);

        String annees = years == 1 ? years + " an" : years > 1 ? years + " ans" : "";
        String moi = months >= 1 ? months + " mois" : "";
        String jour = days == 1 ? days + " jour" : days > 1 ? days + " jours" : "";
        String heure = hours == 1 ? hours + " heure" : hours > 1 ? hours + " heures" : "";
        String minute = minutes == 1 ? minutes + " minute" : minutes > 1 ? minutes + " minutes" : "";
        String second = seconds == 1 ? seconds + " seconde" : seconds > 1 ? seconds + " secondes" : "";
        String diff = "";
        if (!annees.equals( "")) {
            diff = annees;
        } else if (!moi.equals( "")) {
            diff = moi;
        } else if (!jour.equals( "")) {
            diff = jour;
        } else if (!heure.equals( "")) {
            diff = heure;
        } else if (!minute.equals( "")) {
            diff = minute;
        } else if (!second.equals( "")) {
            diff = second;
        }

        return diff;
    }
}
