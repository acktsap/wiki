package acktsap.core.datetime;

import java.time.MonthDay;
import java.time.Period;
import java.time.Year;
import java.time.temporal.ChronoUnit;

public class YearTest {

    // Year : represents a year
    public static void main(String[] args) {
        /* creation */

        // now
        Year now = Year.now();
        System.out.println("Year.now() : " + now);

        // parse
        Year _2020 = Year.parse("2020");
        System.out.println("Year.parse(\"2020\") : " + _2020);

        /* api */

        Year year = Year.now();

        // atXXX
        System.out.println("year.atDay(60) : " + year.atDay(60));
        System.out.println("year.atMonth(3) : " + year.atMonth(3));

        // isValidMonthDay
        boolean validMonthDay = year.isValidMonthDay(MonthDay.of(2, 29));
        System.out.println("year.isValidMonthDay(MonthDay.of(2, 29)) : " + validMonthDay);

        // plus, minus
        Year plus = year.plus(Period.ofYears(2));
        System.out.println("year.plus(Period.ofDays(1)) : " + plus);
        Year minus = year.minus(3, ChronoUnit.YEARS);
        System.out.println("year.minus(3, ChronoUnit.YEARS) : " + minus);
    }

}
