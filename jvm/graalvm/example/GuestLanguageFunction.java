import org.graalvm.polyglot.*; // import polyglot api
import org.graalvm.polyglot.proxy.*; // import polyglot proxy api;

public class GuestLanguageFunction {
    public static void main(String[] args) {
        try (Context context = Context.create()) {
            // Value: function에 대한 java reference
            Value function = context.eval("js", "x => x+1");
            System.out.println("canExecute: " + function.canExecute());
            int x = function.execute(41).asInt(); // execute js function
            assert x == 42;
            System.out.printf("x is %d%n", x);
        }
    }
}
