package acktsap.core.datetime;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class DayOfWeekTest {

    // DayOfWeek : MONDAY through SUNDAY
    public static void main(String[] args) {
        /* creation */

        // from TemporalAccessor
        DayOfWeek now = DayOfWeek.from(LocalDate.now());
        System.out.println("DayOfWeek.from(LocalDate.now()) : " + now);

        // of number
        DayOfWeek monday = DayOfWeek.of(1); // 1 ~ 7 (MON ~ SUN)
        System.out.println("DayOfWeek.of(1) : " + monday);

        // enum use
        DayOfWeek tuesday = DayOfWeek.TUESDAY;
        System.out.println("DayOfWeek.TUESDAY : " + tuesday);


        /* api */

        DayOfWeek dayOfWeek = DayOfWeek.from(LocalDate.now());

        // plus & minus
        System.out.println("dayOfWeek.plus(3) : " + dayOfWeek.plus(3));  // prints THURSDAY

        // print by locale
        System.out.println("== Print by locale ==");
        Locale defaultLocale = Locale.getDefault();
        System.out.println(dayOfWeek.getDisplayName(TextStyle.FULL, defaultLocale));
        System.out.println(dayOfWeek.getDisplayName(TextStyle.SHORT, defaultLocale));
        System.out.println(dayOfWeek.getDisplayName(TextStyle.NARROW, defaultLocale));
        Locale germany = Locale.GERMANY;
        System.out.println(dayOfWeek.getDisplayName(TextStyle.FULL, germany));
        System.out.println(dayOfWeek.getDisplayName(TextStyle.SHORT, germany));
        System.out.println(dayOfWeek.getDisplayName(TextStyle.NARROW, germany));
    }

}
