/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.pattern.reflection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
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

    Object run1(Object obj);

    Object run2(Object obj);
  }

  static class TestImpl implements Test {

    @Override
    public Object run1(Object obj) {
      return "TestImpl::run1";
    }

    @Override
    public Object run2(Object obj) {
      return "TestImpl::run2";
    }

  }

  static class CustomInvocationHandler1 implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method m, Object[] args)
        throws InvocationTargetException, IllegalAccessException {
      System.out.println("method: " + m.getName() + ", args: " + Arrays.toString(args));
      return "CustomInvocationHandler::invoke";
    }

  }

  static class CustomInvocationHandler2 implements InvocationHandler {

    Test delegate;

    CustomInvocationHandler2(Test test) {
      this.delegate = test;
    }

    @Override
    public Object invoke(Object proxy, Method m, Object[] args)
        throws InvocationTargetException, IllegalAccessException {
      System.out.println("method: " + m.getName() + ", args: " + Arrays.toString(args));
//      return "CustomInvocationHandler::invoke";
      return m.invoke(delegate, args);
    }

  }

  public static void main(String[] args) {
    // not using impl
    Test foo = (Test) Proxy.newProxyInstance(
        Test.class.getClassLoader(),
        new Class[]{Test.class},
        new CustomInvocationHandler1());
    System.out.println(foo.run1("test"));
    System.out.println(foo.run2("test"));

    // using impl
    Test bar = (Test) Proxy.newProxyInstance(
        Test.class.getClassLoader(),
        new Class[]{Test.class},
        new CustomInvocationHandler2(new TestImpl()));
    System.out.println(bar.run1("test"));
    System.out.println(bar.run2("test"));
  }

}
