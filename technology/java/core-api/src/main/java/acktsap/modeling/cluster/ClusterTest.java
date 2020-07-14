package acktsap.modeling.cluster;

import static java.util.Arrays.asList;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/*
 * See also
 *
 *
 * connection target
 *
 * https://github.com/web3j/web3j/blob/master/core/src/main/java/org/web3j/protocol/Web3j.java
 * https://github.com/klaytn/caver-java https://github.com/icon-project/icon-sdk-java
 *
 * hikaripool behavior
 *
 * https://medium.com/@rajchandak1993/understanding-hikaricps-connection-pooling-behaviour-
 * 467c5a1a1506
 *
 */
public class ClusterTest {

  protected static class ConnectionException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -6974197105494181255L;
  }

  protected static class ConnectionImpl implements Connection {
  }

  protected static class ClientImpl implements Client {

    protected Connection connection;

    @Override
    public void setConnection(Connection connection) {
      this.connection = connection;
    }

    @Override
    public void request() {
      boolean success = new Random().nextBoolean();
      if (!success) {
        System.out.println("Request failed with " + this.connection);
        throw new ConnectionException();
      }
      System.out.println("Request success with " + this.connection);
    }

  }

  protected static class ConnectionFailoverRunner implements FailoverRunner {

    protected final Queue<Connection> connectionQueue = new LinkedList<>();

    ConnectionFailoverRunner(Collection<Connection> connections) {
      this.connectionQueue.addAll(connections);
    }

    @Override
    public synchronized <R> R submit(Object possiblyBroken, Invocation<R> invocation)
        throws Throwable {
      int count = connectionQueue.size();
      System.out.println("--- Connection count: " + count + " ---\n");
      boolean success = false;
      R ret = null;
      Throwable cause = null;
      while (!success && 0 < count) {
        try {
          System.out.println("Try (left: " + count + ")");
          Connection conn = this.connectionQueue.peek();
          headToTail();
          ((Client) possiblyBroken).setConnection(conn);
          ret = invocation.invoke(possiblyBroken);
          success = true;
        } catch (ConnectionException e) {
          cause = e;
          --count;
        } catch (Throwable e) {
          cause = e;
          break;
        }
      }

      if (!success) {
        throw cause;
      }

      return ret;
    }

    protected void headToTail() {
      Connection head = this.connectionQueue.poll();
      this.connectionQueue.add(head);
    }
  }

  protected static class FailoverInvocationHandler<T> implements InvocationHandler {

    protected final T delegate;

    protected final FailoverRunner runner;

    FailoverInvocationHandler(T delegate, FailoverRunner runner) {
      this.delegate = delegate;
      this.runner = runner;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      try {
        Class<?>[] argClasses = null;
        if (null != args && 0 < args.length) {
          argClasses = new Class[args.length];
          for (int i = 0; i < args.length; ++i) {
            argClasses[i] = args[i].getClass();
          }
        } else {
          argClasses = new Class[0];
        }
        Method m = delegate.getClass().getMethod(method.getName(), argClasses);

        return runner.submit(delegate, (d) -> {
          try {
            m.invoke(d, args);
          } catch (InvocationTargetException e) {
            throw e.getTargetException();
          } catch (Exception e) {
            throw e;
          }
          return null;
        });
      } catch (Throwable e) {
        throw e;
      }
    }
  }

  protected static class ClusteredFactoryImpl implements ClusteredFactory<Client> {

    @Override
    public Client create(Connection... connections) {
      Client delegate = new ClientImpl();
      ConnectionFailoverRunner runner = new ConnectionFailoverRunner(asList(connections));
      InvocationHandler handler = new FailoverInvocationHandler<Client>(delegate, runner);
      return (Client) Proxy.newProxyInstance(getClass().getClassLoader(),
          new Class[] {Client.class},
          handler);
    }

  }

  public static void main(String[] args) {
    Connection conn1 = new ConnectionImpl();
    Connection conn2 = new ConnectionImpl();
    Connection conn3 = new ConnectionImpl();

    ClusteredFactory<Client> factory = new ClusteredFactoryImpl();
    Client clustered = factory.create(conn1, conn2, conn3);

    // change connection and retry on connection failure
    clustered.request();
  }

}
