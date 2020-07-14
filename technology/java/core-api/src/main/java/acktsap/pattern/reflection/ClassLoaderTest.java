/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.pattern.reflection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.SecureClassLoader;

/**
 * Reflection : Class에 대한 정보를 추출할 수 있는 Java에서 제공해 주는 API
 */
public class ClassLoaderTest {

  protected static class MyBaseClass {

    public String toPrettyString() {
      return "";
    }

  }

  public static void main(String[] args) {
    final Object a = "";
    a.getClass().getDeclaredMethods();

    /**
     * System class - AppClassLoader loads Servlet class loader
     * 
     * Was vs framework: lib안에 있는 같은 클래스가 우선순위가 높으냐 낮으냐
     * 
     * Class loader can be tree
     */
    ClassLoader classLoader = new SecureClassLoader(ClassLoader.getSystemClassLoader()) {

      /*
       * loadClass defineClass
       * 
       * child-first class loader vs parent-first class loader
       * 
       * class loader leak : 이거땜에 만들기 어려 Class -> ClassLoader -> RootSet
       * 
       * Strong, Soft, Weak, Phantom reference 생각해
       */
      @Override
      protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
          FileInputStream in = new FileInputStream(name.replace('.', '/') + ".class");
          // return defineClass(name, in);
          return super.findClass(name);
        } catch (FileNotFoundException e) {
          throw new IllegalStateException(e);
        }
      }

    };
  }

}
