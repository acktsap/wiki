package acktsap.pattern.datetime;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

public class MonthTest {

    // Month : JANUARY through DECEMBER
    public static void main(String[] args) {
        /* creation */

        // from TemporalAccessor
        Month now = Month.from(LocalDate.now());
        System.out.println("Month.from(LocalDate.now()) : " + now);

        // enum use
        Month february = Month.FEBRUARY;
        System.out.println("Month.FEBRUARY : " + february);


        /* api */

        Month month = Month.from(LocalDate.now());

        // print min/max length
        System.out.println("month.minLength() : " + month.minLength());
        System.out.println("month.maxLength() : " + month.maxLength());

        // gets the day-of-year corresponding to the first day of this mont
        // leapYear (윤년) 에 따라 하루 차이남
        System.out.println("month.firstDayOfYear(false) : " + month.firstDayOfYear(false));
        System.out.println("month.firstDayOfYear(true) : " + month.firstDayOfYear(true));

        // get first month of this quarter
        System.out.println("month.firstMonthOfQuarter() : " + month.firstMonthOfQuarter());

        // print by locale
        System.out.println("== Print by locale ==");
        Locale defaultLocale = Locale.getDefault();
        System.out.println(month.getDisplayName(TextStyle.FULL, defaultLocale));
        System.out.println(month.getDisplayName(TextStyle.NARROW, defaultLocale));
        System.out.println(month.getDisplayName(TextStyle.SHORT, defaultLocale));
        Locale germany = Locale.GERMANY;
        System.out.println(month.getDisplayName(TextStyle.FULL, germany));
        System.out.println(month.getDisplayName(TextStyle.NARROW, germany));
        System.out.println(month.getDisplayName(TextStyle.SHORT, germany));
    }

}
