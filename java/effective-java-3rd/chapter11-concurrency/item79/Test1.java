import java.util.*;

// Simple test of ObservableSet - Page 318
public class Test1 {
    public static void main(String[] args) {
        ObservableSet<Integer> set = new ObservableSet<>(new HashSet<>());

        // finish successfully
        set.addObserver((s, e) -> System.out.println(e));
        for (int i = 0; i < 100; i++)
            set.add(i);
    }
}
