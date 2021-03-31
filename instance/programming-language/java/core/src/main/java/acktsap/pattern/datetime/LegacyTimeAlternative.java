package acktsap.pattern.datetime;

import acktsap.pattern.Pattern;

import java.time.Instant;
import java.util.Date;

public class LegacyTimeAlternative {

    public static void main(String[] args) {
        Pattern.d("Instant is alternative for Date (it holds UTC)").p(() -> {
            Instant instant = Instant.now();
            Date date = Date.from(instant);
            System.out.println("Date <- Instant: " + date);
            System.out.println("Date -> Instant: " + date.toInstant());
        });
    }
}
