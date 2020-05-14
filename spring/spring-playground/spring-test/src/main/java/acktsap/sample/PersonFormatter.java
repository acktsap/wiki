package acktsap.sample;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PersonFormatter {

  protected static class Test implements Callable<List<Integer>> {

    protected final List<Integer> times;
    protected final List<Integer> valueTimes;
    protected final List<Integer> values;

    public Test(List<Integer> times, List<Integer> valueTimes, List<Integer> values) {
      this.times = times;
      this.valueTimes = valueTimes;
      this.values = values;
    }

    @Override
    public List<Integer> call() throws Exception {
      final List<Integer> ret = new ArrayList<>();
      int insertIndex = 0;
      for (int i = 0; i < values.size(); ++i) {
        final int time = valueTimes.get(i);
        final int value = values.get(i);
        while (times.get(insertIndex) != time) {
          ret.add(null);
          ++insertIndex;
        }
        ret.add(value);
        ++insertIndex;
      }
      while (insertIndex < times.size()) {
        ret.add(null);
        ++insertIndex;
      }

//      int index=0;
//      for(int i=0; i<times.size(); i++){
//        for(int j=index; j<valueTimes.size(); j++){
//          if(times.get(i)==valueTimes.get(j)){
//            index=j + 1;
//            ret.add(values.get(j));
//          }
//          else{
//            ret.add(null);
//          }
//        }
//
//      }
      return ret;
    }

  }

  public static void main(String[] args) {
    List<Integer> aTime = asList(1, 2);
    List<Integer> aValue = asList(3, 4);
    List<Integer> bTime = asList(1, 1, 2, 2);
    List<Integer> bValue = asList(8, 9, 8, 9);
    List<Integer> cTime = asList(2, 2, 3, 3, 4);
    List<Integer> cValue = asList(8, 9, 8, 9, 8);
    List<Integer> times = asList(1, 1, 2, 2, 3, 3, 4);

    ExecutorService executorService = Executors
        .newFixedThreadPool(2 * Runtime.getRuntime().availableProcessors());

    List<Future<List<Integer>>> futures = new ArrayList<>();
    futures.add(executorService.submit(new Test(times, aTime, aValue)));
    futures.add(executorService.submit(new Test(times, bTime, bValue)));
    futures.add(executorService.submit(new Test(times, cTime, cValue)));

    List<List<Integer>> results = futures.stream().map(f -> {
      try {
        return f.get();
      } catch (Exception e) {
        throw new IllegalStateException(e);
      }
    }).collect(toList());
    System.out.println(results);
    executorService.shutdown();
  }

}
