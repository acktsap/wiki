import java.util.ArrayList;
import java.util.List;

public class MemoryPig {

  public static final int initial = 1;
  public static final int factor = 2;

  public static void main(String[] args) throws Exception {
    int iteratorValue = MemoryPig.initial;
    System.out.println("\n=================> OOM test started..\n");
    int i = 0;
    while (true) {
      System.out.println("Iteration " + i + " Free Mem: " + Runtime.getRuntime().freeMemory());
      System.out.println("Required Memory for current loop: " + iteratorValue + "\n");
      int[] memoryFillIntVar = new int[iteratorValue];
      iteratorValue *= MemoryPig.factor;
      Thread.sleep(1000);
      ++i;
    }
  }

}