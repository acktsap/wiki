/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.pattern.io;

import acktsap.Block;

import java.io.InputStream;
import java.util.Properties;

public class ResourcesIO {

    public static void main(String[] args) {
        Block.d("Load by Class").p(() -> {
            Properties properties = new Properties();
            try (InputStream in = ResourcesIO.class.getResourceAsStream("/resources.properties")) {
                properties.load(in);
            }

            System.out.println(properties);
            System.out.println("key=" + properties.getProperty("key"));
            System.out.println("novalue=" + properties.getProperty("novalue"));
            System.out.println("nokey=" + properties.getProperty("nokey", "default value"));
        });

        Block.d("Load by ClassLoader").p(() -> {
            Properties properties = new Properties();
            try (InputStream in = ResourcesIO.class.getClassLoader().getResourceAsStream("resources.properties")) {
                properties.load(in);
            }

            System.out.println(properties);
            System.out.println("key=" + properties.getProperty("key"));
            System.out.println("novalue=" + properties.getProperty("novalue"));
            System.out.println("nokey=" + properties.getProperty("nokey", "default value"));
        });
    }

}
