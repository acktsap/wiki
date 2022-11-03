import java.util.concurrent.Callable;
import org.graalvm.polyglot.*; // import polyglot api
import org.graalvm.polyglot.proxy.*; // import polyglot proxy api;

public class AccessJavaFromGuestLanguage {
    public static class MyClass {
        public int               id    = 42;
        public String            text  = "42";
        public int[]             arr   = new int[]{1, 42, 3};
        public Callable<Integer> ret42 = () -> 42;
    }

    public static void main(String[] args) {
        try (Context context = Context.newBuilder()
                               .allowAllAccess(true) // guest language에 java 접근 허가 설정
                           .build()) {

            context.getBindings("js").putMember("javaObj", new MyClass());
            boolean result = context.eval("js",
                "    javaObj.id         == 42"          +
                " && javaObj.text       == '42'"        +
                " && javaObj.arr[1]     == 42"          +
                " && javaObj.ret42()    == 42")
            .asBoolean();
            System.out.println("eval result: " + result);
            assert result == true;
        }
    }
}
