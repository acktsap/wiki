package acktsap.syntax.clazz;

class InnerEx1 {

    class InstanceInner {

        int iv = 100;
        //          static int cv = 100; // non-static class에서 static 변수 선언 ㄴㄴ
        final static int CONST = 100;   // final static은 상수이므로 허용
    }

    static class StaticInner {

        int iv = 200;
        static int cv = 200;
    }

    void myMethod() {
        class LocalInner {

            int iv = 300;
            //          static int cv = 300; // non-static class에서 static 변수 선언 ㄴㄴ
            final static int CONST = 300; // final static은 상수이므로 허용
        }
    }

    public static void main(String args[]) {
        System.out.println(InstanceInner.CONST);
        System.out.println(StaticInner.cv);
    }
}
