import org.graalvm.polyglot.*; // import polyglot api
import org.graalvm.polyglot.proxy.*; // import polyglot proxy api;

public class AccessGuestLanguageDirectlyFromJava {
    public static void main(String[] args) {
        try (Context context = Context.create()) {
            Value result = context.eval("js", 
                            "({ "                   +
                                "id   : 42, "       +
                                "text : '42', "     +
                                "arr  : [1,42,3] "  +
                            "})");
            System.out.println("hasMembers: " + result.hasMembers());

            int id = result.getMember("id").asInt();
            System.out.println("member.id: " + id);
            assert id == 42;

            String text = result.getMember("text").asString();
            System.out.println("member.text: " + text);
            assert text.equals("42");

            Value array = result.getMember("arr");
            System.out.println("member.array: " + array);
            assert array.hasArrayElements();
            assert array.getArraySize() == 3;
            assert array.getArrayElement(1).asInt() == 42;
        }
    }
}
