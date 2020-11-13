package acktsap.core.datetime;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;

public class LocalDateTest {

  // LocalDate : a date without a time
  public static void main(String[] args) {
    /* creation */

    // of
    LocalDate date = LocalDate.of(2000, Month.NOVEMBER, 20);
    System.out.println("LocalDate.of(2000, Month.NOVEMBER, 20) : " + date);

    // now
    LocalDate now = LocalDate.now();
    System.out.println("LocalDate.now() : " + now);

    // parse
    LocalDate parsed = LocalDate.parse("2019-11-07");
    System.out.println("LocalDate.parse(\"2019-11-07\") : " + parsed);


    /* api */

    LocalDate localDate = LocalDate.now();

    // with
    LocalDate nextWed = localDate.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));
    System.out.println("localDate.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY)) : " + nextWed);

    // getDayOfWeek
    DayOfWeek dayOfWeek = localDate.getDayOfWeek();
    System.out.println("localDate.getDayOfWeek() : " + dayOfWeek);

    // comparision
    LocalDate epochDay = LocalDate.ofEpochDay(0);
    System.out.println("localDate.isAfter(epochDay) : " + localDate.isAfter(epochDay));

    // https://docs.oracle.com/javase/tutorial/datetime/iso/date.html
  }

}
