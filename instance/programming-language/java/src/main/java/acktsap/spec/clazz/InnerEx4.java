package acktsap.spec.clazz;

import acktsap.spec.clazz.InnerEx4.Outer.InstanceInner;
import acktsap.spec.clazz.InnerEx4.Outer.StaticInner;

public class InnerEx4 {

    static class Outer {

        // non static
        class InstanceInner {

            int iv = 100;
        }

        // static
        static class StaticInner {

            int iv = 200;
            static int cv = 300;
        }
    }

    public static void main(String[] args) {
        // 인스턴스클래스의 인스턴스를 생성하려면 외부 클래스의 인스턴스를 먼저 생성해야 한다.
        Outer outer = new Outer();
        InstanceInner instanceInner = outer.new InstanceInner();
        System.out.println("ii.iv : " + instanceInner.iv); // 100

        // 스태틱 내부 클래스의 인스턴스는 외부 클래스를 먼저 생성하지 않아도 된다.
        StaticInner staticInner = new StaticInner();
        System.out.println("si.iv : " + staticInner.iv); // 200
        System.out.println("Outer.StaticInner.cv : " + Outer.StaticInner.cv); // 300
    }
}
