package acktsap.snippet.datetime;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class LocalTimeTest {

    // LocalTime : time only
    // representing human-based time of day, such as movie times, or the opening and closing times
    public static void main(String[] args) {
        /* creation */

        // now
        LocalTime now = LocalTime.now();
        System.out.println("LocalTime.now() : " + now);

        // or
        LocalTime _0200 = LocalTime.of(2, 0);
        System.out.println("LocalTime.of(2, 0) : " + _0200);

        /* api */
        LocalTime localTime = LocalTime.now();

        // atDate
        LocalDateTime ofLocalDate = localTime.atDate(LocalDate.of(2020, 01, 21));
        System.out.println("localTime.atDate(LocalDate.of(2020, 01, 21)); : " + localTime);

        // plus, minus
        LocalTime plus = localTime.plus(2, ChronoUnit.HOURS);
        LocalTime minus = localTime.minus(Duration.ofHours(3));
        System.out.println("localTime.plus(2, ChronoUnit.HOURS) : " + plus);
        System.out.println("localTime.minus(Duration.ofHours(3)) : " + minus);
    }

}
