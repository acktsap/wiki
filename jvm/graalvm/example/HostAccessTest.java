import org.graalvm.polyglot.*; // import polyglot api
import org.graalvm.polyglot.proxy.*; // import polyglot proxy api;

public class HostAccessTest {
    public static class Employee {
        private final String name;

        Employee(String name) {this.name = name;}

        @HostAccess.Export
        public String getName() {
            return name;
        }
    }

    public static class Services {
        @HostAccess.Export
        public Employee createEmployee(String name) {
            return new Employee(name);
        }
        
        public void exitVM() {
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        try (Context context = Context.create()) {
            Services services = new Services();
            context.getBindings("js").putMember("services", services);
            String name = context.eval("js",
                    "let emp = services.createEmployee('John Doe');" + 
                    "emp.getName()").asString();
            assert name.equals("John Doe");
            System.out.println("name: " + name);
            
            try {
                context.eval("js", "services.exitVM()");
                assert false;
            } catch (PolyglotException e) {
                System.out.println(e);
                assert e.getMessage().endsWith(
                        "Unknown identifier: exitVM");
            }
        }
    }
}
