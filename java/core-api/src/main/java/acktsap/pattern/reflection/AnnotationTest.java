package acktsap.pattern.reflection;

import acktsap.pattern.reflection.AnnotationTest.DateTime;
import acktsap.pattern.reflection.AnnotationTest.TestInfo;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Reflection : Class에 대한 정보를 추출할 수 있는 Java에서 제공해 주는 API
 */
@Deprecated
@TestInfo(testedBy = "aaa", testDate = @DateTime(yymmdd = "160101", hhmmss = "235959"))
public class AnnotationTest {

  private String testedBy = "unset";

  public String getTestedBy() {
    return testedBy;
  }

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
    AnnotationTest test = new AnnotationTest();
    Class<? extends AnnotationTest> clazz = test.getClass();

    // get TestInfo annotation
    TestInfo testInfo = clazz.getAnnotation(TestInfo.class);
    System.out.println("anno.testedBy()=" + testInfo.testedBy());
    System.out.println("anno.testDate().yymmdd()=" + testInfo.testDate().yymmdd());
    System.out.println("anno.testDate().hhmmss()=" + testInfo.testDate().hhmmss());
    System.out.println("anno.testTools=" + Arrays.toString(testInfo.testTools()));
    System.out.println();

    // get all annotations
    System.out.println("All annotations");
    System.out.println(Arrays.toString(clazz.getAnnotations()));

    // inject testby by annotation
    try {
      System.out.println("Fields: " + Arrays.toString(clazz.getDeclaredFields()));
      System.out.println("Before inject: " + test.getTestedBy());
      Field field = clazz.getDeclaredField("testedBy");
      field.set(test, testInfo.testedBy());
      System.out.println("After inject: " + test.getTestedBy());
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }
}
