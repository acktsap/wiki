import org.graalvm.polyglot.*; // import polyglot api
import org.graalvm.polyglot.proxy.*; // import polyglot proxy api;

public class HelloPolyglot {
    public static void main(String[] args) {
        System.out.println("Hello Java!");
        // Context : guest language에 대한 실행 환경을 제공
        // 이거도 close 해야 해서 try-with-resource 사용
        try (Context context = Context.create()) {
            context.eval("js", "print('Hello JavaScript!');");
        }
    }
}
