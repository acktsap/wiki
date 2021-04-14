package acktsap.pattern.datetime;

import acktsap.Block;

import java.time.Instant;
import java.util.Date;

public class LegacyTimeAlternative {

    public static void main(String[] args) {
        Block.d("Instant is alternative for Date (it holds UTC)").p(() -> {
            Instant instant = Instant.now();
            Date date = Date.from(instant);
            System.out.println("Date <- Instant: " + date);
            System.out.println("Date -> Instant: " + date.toInstant());
        });
    }
}
