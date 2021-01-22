package acktsap.core.datetime;

import java.time.YearMonth;

import static java.time.Month.FEBRUARY;

public class YearMonthTest {

    // YearMonth : the month of a specific year
    public static void main(String[] args) {
        /* creation */

        // now
        YearMonth now = YearMonth.now();
        System.out.println("YearMonth.now() : " + now);

        // of
        YearMonth of = YearMonth.of(2010, FEBRUARY);
        System.out.println("YearMonth.of(2010, FEBRUARY) : " + of);

        // parse
        YearMonth parsed = YearMonth.parse("2020-11");
        System.out.println("YearMonth.parse(\"2020-11\") : " + parsed);


        /* api */

        YearMonth yearMonth = YearMonth.now();

        // comparision
        YearMonth epoch = YearMonth.of(1970, 1);
        System.out.println("yearMonth.isAfter(epoch) : " + yearMonth.isAfter(epoch));

        // isLeapYear (윤년)
        System.out.println("yearMonth.isLeapYear() : " + yearMonth.isLeapYear());

        // atDay (returns LocalDate)
        System.out.println("yearMonth.atDay(20) : " + yearMonth.atDay(20));
    }

}
