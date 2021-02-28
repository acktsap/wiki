package acktsap.core.datetime;

import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;

public class MonthDayTest {

    // MonthDay : the day of a particular month
    public static void main(String[] args) {
        /* creation */

        // now
        MonthDay now = MonthDay.now();
        System.out.println("MonthDay.now() : " + now);

        // of
        MonthDay _0312 = MonthDay.of(3, 12);
        MonthDay _0412 = MonthDay.of(Month.APRIL, 12);
        System.out.println("MonthDay.of(3, 12) : " + _0312);
        System.out.println("MonthDay.of(Month.APRIL, 12) : " + _0412);

        /* api */
        MonthDay monthDay = MonthDay.now();

        // with
        MonthDay with1 = monthDay.with(Month.JANUARY);
        MonthDay with2 = monthDay.withMonth(1);
        System.out.println("monthDay.with(Month.JANUARY) : " + with1);
        System.out.println("monthDay.withMonth(1) : " + with2);

        // atYear
        LocalDate atYear = monthDay.atYear(2020);
        System.out.println("monthDay.atYear(2020) : " + atYear);

        // isValidYear
        MonthDay _0229 = MonthDay.of(Month.FEBRUARY, 29);
        System.out.println("_0229.isValidYear(2010) : " + _0229.isValidYear(2010));
        System.out.println("_0229.isValidYear(2012) : " + _0229.isValidYear(2012));
    }

}
