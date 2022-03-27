import java.util.*;

// More complex test of ObservableSet - Page 318-9
public class Test2 {
    public static void main(String[] args) {
        ObservableSet<Integer> set = new ObservableSet<>(new HashSet<>());

        // throws ConcurrentModificationException
        set.addObserver(new SetObserver<Integer>() {
            public void added(ObservableSet<Integer> s, Integer e) {
                System.out.println(e);
                if (e == 23)
                    s.removeObserver(this);
            }
        });
        for (int i = 0; i < 100; i++)
            set.add(i);
    }
}
