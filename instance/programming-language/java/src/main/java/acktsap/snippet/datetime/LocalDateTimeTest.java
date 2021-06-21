package acktsap.snippet.datetime;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class LocalDateTimeTest {

    // LocalDateTime : handles both date and time, without a time zone
    // a combination of LocalDate with LocalTime
    public static void main(String[] args) {
        /* creation */

        // now
        LocalDateTime now = LocalDateTime.now();
        System.out.println("LocalDateTime.now() : " + now);

        // of LocalXXX
        LocalDate localDate = LocalDate.of(2020, 1, 21);
        LocalTime localTime = LocalTime.of(3, 24);
        LocalDateTime ofLocalSeries = LocalDateTime.of(localDate, localTime);
        System.out
            .println("LocalDateTime.of(LocalDateTime.of(localDate, localTime) : " + ofLocalSeries);

        // of all
        LocalDateTime ofAll = LocalDateTime.of(2020, 1, 21, 2, 34);
        System.out.println("LocalDateTime.of(2020, 1, 21, 2, 34) : " + ofAll);

        // ofInstant
        ZoneId zoneId = ZoneId.of("Asia/Seoul");
        LocalDateTime ofInstant = LocalDateTime.ofInstant(Instant.now(), zoneId);
        System.out.println("LocalDateTime.ofInstant(Instant.now(), zoneId) : " + ofInstant);

        /* api */

        LocalDateTime localDateTime = LocalDateTime.now();

        // plus, minus
        LocalDateTime plus = localDateTime.plus(Period.ofYears(3));
        LocalDateTime minus = localDateTime.minus(5, ChronoUnit.HOURS);
        System.out.println("localDateTime.plus(Period.ofYears(3)) : " + plus);
        System.out.println("localDateTime.minus(5, ChronoUnit.HOURS) : " + minus);

        // withXXX
        LocalDateTime withHour = localDateTime.withHour(23);
        LocalDateTime withNano = localDateTime.withNano(1);
        System.out.println("localDateTime.withHour(23) : " + withHour);
        System.out.println("localDateTime.withNano(1) : " + withNano);
    }

}
