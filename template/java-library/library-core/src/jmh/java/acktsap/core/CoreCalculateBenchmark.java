/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@BenchmarkMode(Mode.Throughput)
public class CoreCalculateBenchmark {

  @State(Scope.Thread)
  public static class User {

    protected Core core;

    @Setup(Level.Trial)
    public void setUp() {
      final InputStream in = getClass().getResourceAsStream("/test.txt");
      final Scanner scanner = new Scanner(in);
      while(scanner.hasNext()) {
        System.out.println(scanner.next());
      }
      core = new Core();
    }

    public void calculate() throws IOException {
      core.calculate(1, 2);
    }

  }

  @Benchmark
  public void bind(final User user) throws IOException {
    user.calculate();
  }

}
