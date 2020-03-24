package acktsap.pattern.clazz;

class InnerEx2 {

  class InstanceInner {

  }

  static class StaticInner {

  }

  InstanceInner iv1 = new InstanceInner();
//  static InstanceInner iv2 = new InstanceInner(); // static class가 아니라서 불가능

  StaticInner cv1 = new StaticInner();
  static StaticInner cv2 = new StaticInner();

  static void staticMethod() {
//		InstanceInner obj1 = new InstanceInner(); // InstanceInner가 static이 아니라서 불가능
    StaticInner obj2 = new StaticInner();

    InnerEx2 outer = new InnerEx2();
    InstanceInner obj3 = outer.new InstanceInner(); // 이렇게는 가능
  }

  void instanceMethod() {
    InstanceInner obj1 = new InstanceInner();
    StaticInner obj2 = new StaticInner();

    // 메서드 내에 지역적으로 선언된 내부 클래스는 외부에서 접근할 수 없다.
//		LocalInner lv = new LocalInner();
  }

  void myMethod() {
    class LocalInner {

    }
    LocalInner lv = new LocalInner();
  }
}