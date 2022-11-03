import org.graalvm.polyglot.*; // import polyglot api
import org.graalvm.polyglot.proxy.*; // import polyglot proxy api;

public class LookupJavaTypeFromGuestLanguage {
    public static void main(String[] args) {
        try (Context context = Context.newBuilder()
                           .allowAllAccess(true) // Guest language에 접근 허용
                       .build()) {
            java.math.BigDecimal v = context.eval("js",
                    "var BigDecimal = Java.type('java.math.BigDecimal');" +
                    "BigDecimal.valueOf(10).pow(20)")
                .asHostObject();
            System.out.println("v: " + v);
            assert v.toString().equals("100000000000000000000");
        }
    }
}
