package acktsap.snippet.datetime;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

import acktsap.Block;

public class DayOfWeekTest {

    // DayOfWeek : MONDAY through SUNDAY
    public static void main(String[] args) {
        Block.d("Creation", () -> {
            // from TemporalAccessor (LocalDate)
            DayOfWeek fromLocalDate = DayOfWeek.from(LocalDate.now());
            System.out.println(fromLocalDate);

            // from TemporalAccessor (LocalDateTime)
            DayOfWeek fromLocalDateTime = DayOfWeek.from(LocalDateTime.now());
            System.out.println(fromLocalDateTime);

            // of
            DayOfWeek ofNumber = DayOfWeek.of(1); // 1 ~ 7 (MON ~ SUN)
            System.out.println(ofNumber);

            // enum use
            System.out.println(DayOfWeek.TUESDAY);
        });

        Block.d("Plus & minus", () -> {
            DayOfWeek dayOfWeek = DayOfWeek.from(LocalDate.now());

            System.out.println(dayOfWeek.plus(1));
            System.out.println(dayOfWeek.minus(1));
        });

        Block.d("Print by locale", () -> {
            DayOfWeek dayOfWeek = DayOfWeek.from(LocalDate.now());

            System.out.println("default locale");
            Locale defaultLocale = Locale.getDefault();
            System.out.println(dayOfWeek.getDisplayName(TextStyle.FULL, defaultLocale));
            System.out.println(dayOfWeek.getDisplayName(TextStyle.SHORT, defaultLocale));
            System.out.println(dayOfWeek.getDisplayName(TextStyle.NARROW, defaultLocale));

            System.out.println("germany locale");
            Locale germany = Locale.GERMANY;
            System.out.println(dayOfWeek.getDisplayName(TextStyle.FULL, germany));
            System.out.println(dayOfWeek.getDisplayName(TextStyle.SHORT, germany));
            System.out.println(dayOfWeek.getDisplayName(TextStyle.NARROW, germany));
        });

    }

}
