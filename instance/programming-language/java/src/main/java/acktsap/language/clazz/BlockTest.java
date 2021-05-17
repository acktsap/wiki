package acktsap.language.clazz;

// 클래스 변수 순서 : 기본값 -> 명시적 초기화 -> static block
// 인스턴스 변수 순서 : 기본값 -> 명시적 초기화 -> init block -> 생성자
public class BlockTest {

    protected static class Value {

        protected String val;

        Value(String val) {
            System.out.println(val);
            this.val = val;
        }
    }

    static Value value = new Value("[class] 2. explicit static");

    // static block : class가 loading될 때 한번 실행
    static {
        System.out.println("[class] 3. static { }");
    }

    Value val = new Value("[instance] 2. explicit");

    // init block : instance가 생성될 때 매번 실행
    {
        System.out.println("[instance] 3. { }");
    }

    public BlockTest() {
        System.out.println("[instance] 4. Constructor");
    }

    public static void main(String args[]) {
        System.out.println("BlockTest bt = new BlockTest(); ");
        BlockTest bt = new BlockTest();

        System.out.println("BlockTest bt2 = new BlockTest(); ");
        BlockTest bt2 = new BlockTest();
    }
}
