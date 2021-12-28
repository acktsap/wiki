package acktsap.snippet.datetime;

import java.time.Instant;
import java.util.Date;

import acktsap.Block;

public class LegacyTimeAlternative {

    public static void main(String[] args) {
        Block.d("Instant is alternative for Date (it holds UTC)", () -> {
            Instant instant = Instant.now();
            Date date = Date.from(instant);
            System.out.println("Date <- Instant: " + date);
            System.out.println("Date -> Instant: " + date.toInstant());
        });
    }
}
