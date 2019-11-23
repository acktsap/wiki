/*
 * @copyright defined in LICENSE.txt
 */

package api.concurrency.immutable;

import java.util.Random;
import java.util.UUID;
import java.util.function.Supplier;

public class ImmutableObject {

  protected static final class ImmutableRGB {

    // Values must be between 0 and 255.
    final private int red;
    final private int green;
    final private int blue;
    final private String name;

    private void check(int red,
        int green,
        int blue) {
      if (red < 0 || red > 255
          || green < 0 || green > 255
          || blue < 0 || blue > 255) {
        throw new IllegalArgumentException();
      }
    }

    public ImmutableRGB(int red,
        int green,
        int blue,
        String name) {
      check(red, green, blue);
      this.red = red;
      this.green = green;
      this.blue = blue;
      this.name = name;
    }

    public ImmutableRGB with(int red,
        int green,
        int blue,
        String name) {
      check(red, green, blue);
      return new ImmutableRGB(red, green, blue, name);
    }

    public int getRGB() {
      return ((red << 16) | (green << 8) | blue);
    }

    public String getName() {
      return name;
    }

    public ImmutableRGB invert() {
      return new ImmutableRGB(255 - red,
          255 - green,
          255 - blue,
          "Inverse of " + name);
    }

  }

  public static void main(String[] args) throws InterruptedException {
    final ImmutableRGB color = new ImmutableRGB(0, 0, 0, "Pitch Black");

    final int tryCount = 100_000;
    final Supplier<Integer> colorSupplier = () -> new Random().nextInt(256);
    final Supplier<String> nameSupplier = () -> UUID.randomUUID().toString().split("-")[0];

    final Runnable modifyRun = () -> {
      for (int i = 1; i <= tryCount; ++i) {
        final String expected = nameSupplier.get();
        String actual = null;

        // we don't need synchronized block
        final ImmutableRGB newRgb =
            color.with(colorSupplier.get(), colorSupplier.get(), colorSupplier.get(), expected);
        actual = newRgb.getName();

        if (!expected.equals(actual)) {
          System.err.format("Actual name is different with expected one (Thread: %s, try: %d)\n",
              Thread.currentThread().getName(), i);
          return;
        }
      }
      System.out.format("Operation finished (Thread: %s)\n", Thread.currentThread().getName());
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
