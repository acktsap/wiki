import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Simulation {

  public static final boolean PRINT_STATUS = true;

  public static final int NATION_COUNT = 200;
  public static final long TIME_INTERVAL = 100; // milliseconds

  public static final int INITIAL_PEOPLE_COUNT = 2_000;
  public static final int AGE_LIMIT = 100;
  public static final int REBORN_INTERVAL = 1;
  public static final int REBORN_COUNT = INITIAL_PEOPLE_COUNT / AGE_LIMIT;

  protected Earth earth;

  public Simulation(final int nationCount) {
    this.earth = new Earth(nationCount);
  }

  protected interface Aging {
    Aging pass();
  }

  protected static class People implements Aging {

    protected int age = new Random().nextInt(AGE_LIMIT);

    @Override
    public Aging pass() {
      if (0 < this.age) {
        --this.age;
      }
      return this;
    }

    public boolean isDead() {
      return this.age <= 0;
    }

    @Override
    public String toString() {
      return String.format("People(age=%d)", this.age);
    }

  }

  protected static class Nation implements Aging {

    protected final Collection<People> peoples =
        Collections.newSetFromMap(new ConcurrentHashMap<>());

    protected final String name;

    protected AtomicInteger rebornCountdown = new AtomicInteger(REBORN_INTERVAL);

    public Nation(final String name, final int initialPeopleCount) {
      this.name = name;
      IntStream.range(0, INITIAL_PEOPLE_COUNT).forEach(i -> this.peoples.add(new People()));
    }

    @Override
    public Aging pass() {
      this.peoples.stream().map(People::pass)
          .map(People.class::cast)
          .filter(People::isDead)
          .forEach(p -> this.peoples.remove(p));

      this.rebornCountdown.decrementAndGet();
      if (0 == rebornCountdown.get()) {
        IntStream.range(0, REBORN_COUNT).forEach(i -> this.peoples.add(new People()));
        this.rebornCountdown.set(REBORN_INTERVAL);
      }

      return this;
    }

    @Override
    public String toString() {
      return String.format("Nation %s has %s people", this.name, peoples.size());
    }

  }

  protected static class Earth {

    protected final Collection<Nation> nations;

    public Earth(final int nationCount) {
      this.nations = new ArrayList<>(nationCount);
      IntStream.range(0, nationCount)
          .forEach(i -> this.nations.add(new Nation(Integer.toString(i), INITIAL_PEOPLE_COUNT)));
    }

    public void play() {
      while (true) {
        this.nations.parallelStream().forEach(Aging::pass);
        if (PRINT_STATUS) {
          System.out.println("\n---------- Time goes");
          this.nations.stream().forEach(System.out::println);
        }
        try {
          Thread.sleep(TIME_INTERVAL);
        } catch (InterruptedException e) {
        }
      }
    }

  }

  public void run() {
    this.earth.play();
  }

  public static void main(final String[] args) throws Exception {
    new Simulation(NATION_COUNT).run();
  }

}
