/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.core.concurrency.immutable;

import java.util.Random;
import java.util.UUID;
import java.util.function.Supplier;

public class SynchronizedObject {

    protected static class SynchronizedRGB {

        // Values must be between 0 and 255.
        private int red;
        private int green;
        private int blue;
        private String name;

        private void check(int red,
                           int green,
                           int blue) {
            if (red < 0 || red > 255
                || green < 0 || green > 255
                || blue < 0 || blue > 255) {
                throw new IllegalArgumentException();
            }
        }

        public SynchronizedRGB(int red,
                               int green,
                               int blue,
                               String name) {
            check(red, green, blue);
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.name = name;
        }

        public void set(int red,
                        int green,
                        int blue,
                        String name) {
            check(red, green, blue);
            synchronized (this) {
                this.red = red;
                this.green = green;
                this.blue = blue;
                this.name = name;
            }
        }

        public synchronized int getRGB() {
            return ((red << 16) | (green << 8) | blue);
        }

        public synchronized String getName() {
            return name;
        }

        public synchronized void invert() {
            red = 255 - red;
            green = 255 - green;
            blue = 255 - blue;
            name = "Inverse of " + name;
        }

    }

    public static void main(String[] args) throws InterruptedException {
        final SynchronizedRGB color = new SynchronizedRGB(0, 0, 0, "Pitch Black");

        final int tryCount = 100_000;
        final Supplier<Integer> colorSupplier = () -> new Random().nextInt(256);
        final Supplier<String> nameSupplier = () -> UUID.randomUUID().toString().split("-")[0];

        final Runnable modifyRun = () -> {
            for (int i = 1; i <= tryCount; ++i) {
                final String expected = nameSupplier.get();
                String actual = null;

                // without synchronized, set may be interfered
                // synchronized (color) {
                color.set(colorSupplier.get(), colorSupplier.get(), colorSupplier.get(), expected);
                actual = color.getName();
                // }

                if (!expected.equals(actual)) {
                    System.err.format(
                        "Actual name is different with expected one (Thread: %s, try: %d)%n",
                        Thread.currentThread().getName(), i);
                    return;
                }
            }
            System.out
                .format("Operation finished (Thread: %s)%n", Thread.currentThread().getName());
        };

        final Thread thread1 = new Thread(modifyRun);
        final Thread thread2 = new Thread(modifyRun);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Finished");
    }

}
