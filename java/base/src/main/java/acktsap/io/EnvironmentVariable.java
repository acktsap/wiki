package acktsap.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

import acktsap.Block;

public class FileIO {

    public static void main(String[] args) {
        Block.d("Make temp file", () -> {
            File file = File.createTempFile("prefix", ".txt");
            try (FileOutputStream os = new FileOutputStream(file)) {
                os.write("i test".getBytes(StandardCharsets.UTF_8));
            }
            System.out.println("file: " + file);

            try (FileInputStream is = new FileInputStream(file)) {
                String content = new String(is.readAllBytes());
                System.out.println("content: " + content);
            }
        });
    }
}
