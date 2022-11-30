import java.util.concurrent.*;

// Concurrent canonicalizing map atop ConcurrentMap - Pages 273-274
public class Intern {
    private static final ConcurrentMap<String, String> map =
            new ConcurrentHashMap<>();

    // Concurrent canonicalizing map atop ConcurrentMap - not optimal
    public static String internSlow(String s) {
        String previousValue = map.putIfAbsent(s, s);
        return previousValue == null ? s : previousValue;
    }

    // Concurrent canonicalizing map atop ConcurrentMap - faster!
    // ConcurrentHashMap is optimized for retrieval operations, such as get
    // Therefore, it is worth invoking get initially and calling
    // putIfAbsent only if get indicates that it is necessary
    public static String internFast(String s) {
        String result = map.get(s);
        if (result == null) {
            result = map.putIfAbsent(s, s);
            if (result == null)
                result = s;
        }
        return result;
    }

    public static void main(String[] args) {
        long count = 100_000_000L;

        long slowStart = System.nanoTime();
        for (long i = 0; i < count; ++i) {
            internSlow("item");
        }
        System.out.printf("Slow one: %dns\n", System.nanoTime() - slowStart);

        long fastStart = System.nanoTime();
        for (long i = 0; i < count; ++i) {
            internFast("item");
        }
        System.out.printf("Faster one: %dns\n", System.nanoTime() - fastStart);
    }

}
