/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.pattern.reflection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * A dynamic proxy class is a class that implements a list of interfaces specified at runtime such
 * that a method invocation through one of the interfaces on an instance of the class will be
 * encoded and dispatched to another object through a uniform interface.
 * <p>
 * Dynamic proxy classes are useful to an application or library that needs to provide type-safe
 * reflective dispatch of invocations on objects that present interface APIs.
 */
public class DynamicProxyTest {

  interface Test {

    Object run(Object obj);
  }

  static class CustonInvocationHandler implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method m, Object[] args) {
      System.out.println("method: " + m.getName() + ", args: " + Arrays.toString(args));
      return "test ret";
    }
  }

  public static void main(String[] args) {
    Test foo = (Test) Proxy.newProxyInstance(
        Test.class.getClassLoader(),
        new Class[]{Test.class},
        new CustonInvocationHandler());
    Object ret = foo.run("Hello");
    System.out.println("Return: " + ret);
  }

}
