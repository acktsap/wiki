package acktsap.pattern.reflection;

import acktsap.pattern.reflection.AnnotationTest.DateTime;
import acktsap.pattern.reflection.AnnotationTest.TestInfo;
import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Deprecated
@TestInfo(testedBy = "aaa", testDate = @DateTime(yymmdd = "160101", hhmmss = "235959"))
public class AnnotationTest {

  // 정의할 때
  // @Target : Annotation이 올 수 있는 곳
  // @Retention : 얼마나 지속될건지 (SOURCE, COMPILE, RUNTIME)
  // @Documented : Javadoc에 포함될건지
  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  @interface TestInfo {

    int count() default 1;

    String testedBy();

    String[] testTools() default "JUnit";

    TestType testType() default TestType.FIRST;

    DateTime testDate();
  }

  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  @interface DateTime {

    String yymmdd();

    String hhmmss();
  }

  enum TestType {FIRST, FINAL}

  public static void main(String args[]) {
    Class<AnnotationTest> clazz = AnnotationTest.class;

    // get TestInfo annotation
    TestInfo anno = clazz.getAnnotation(TestInfo.class);
    System.out.println("anno.testedBy()=" + anno.testedBy());
    System.out.println("anno.testDate().yymmdd()=" + anno.testDate().yymmdd());
    System.out.println("anno.testDate().hhmmss()=" + anno.testDate().hhmmss());
    for (String str : anno.testTools()) {
      System.out.println("testTools=" + str);
    }
    System.out.println();

    // get all annotations
    Annotation[] annoArr = clazz.getAnnotations();
    System.out.println("All annotations");
    for (Annotation a : annoArr) {
      System.out.println(a);
    }
  }
}
