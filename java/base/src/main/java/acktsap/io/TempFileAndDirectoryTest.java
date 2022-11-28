package acktsap.io;

import acktsap.Block;

import java.nio.file.Files;
import java.nio.file.Path;

public class TempFileAndDirectoryTest {
    public static void main(String[] args) {
        Block.d("Get temp directory", () -> {
            String tempDirectory = System.getProperty("java.io.tmpdir");
            System.out.println(tempDirectory);
        });

        Block.d("Create temp file", () -> {
            Path tempFile = Files.createTempFile("prefix", "postfix");
            System.out.println(tempFile);
        });
    }
}
