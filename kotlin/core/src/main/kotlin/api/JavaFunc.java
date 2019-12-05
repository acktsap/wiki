package api;

import java.io.IOException;

public class JavaFunc {
    public void run() {
        FuncKt.add4(); // 1
        FuncKt.add4(3); // 3
        FuncKt.add4(3, 4); // 7
    }

    public void run2() throws IOException {
        FuncKt.addException(1, 2);
    }

    public void run3() {
        // actual code of client
        FuncKt.total1(new Student("name", 0, 0));
    }
}
