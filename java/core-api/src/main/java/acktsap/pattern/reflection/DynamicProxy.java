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
public class DynamicProxy {

  interface Foo {

    Object bar(Object obj);
  }

  static class CustonInvocationHandler implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method m, Object[] args) {
      Object result = "test";
      try {
        System.out.println("before method: " + m.getName() + ", args: " + Arrays.toString(args));
      } catch (Exception e) {
        throw new RuntimeException("unexpected invocation exception: " +
            e.getMessage());
      } finally {
        System.out.println("after method " + m.getName());
      }
      return result;
    }
  }

  public static void main(String[] args) {
    Foo foo = (Foo) Proxy.newProxyInstance(
        Foo.class.getClassLoader(),
        new Class[]{Foo.class},
        new CustonInvocationHandler());
    Object ret = foo.bar("Hello");
    System.out.println("Return: " + ret);
  }

}
