package acktsap.string;

import acktsap.Block;

import java.util.List;

public class StringUtilsTest {

    public static void main(String[] args) {
        Block.d("String join", () -> {
            String message = String.join(",", List.of("aaa", "bbb"));
            System.out.println(message);
        });

        Block.d("String format", () -> {
            String message = String.format("String: %s, Integer: %d %n", "test", 4);
            System.out.println(message);
        });
    }

}
