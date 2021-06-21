package acktsap.snippet.string;

import java.util.List;

public class StringUtilsTest {

    public static void main(String[] args) {
        // string join
        String joined = String.join(",", List.of("aaa", "bbb"));
        System.out.println("Joined: " + joined);
    }

}
