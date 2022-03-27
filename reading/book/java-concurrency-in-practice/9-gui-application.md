# 9. GUI Applications

- [9. GUI Applications](#9-gui-applications)
  - [9.1. Why are GUIs Single-threaded?](#91-why-are-guis-single-threaded)
    - [Sequential Event Processing](#sequential-event-processing)
    - [Thread Confinement in Swing](#thread-confinement-in-swing)
  - [9.2. Short-running GUI Tasks](#92-short-running-gui-tasks)
  - [9.3. Long-running GUI Tasks](#93-long-running-gui-tasks)
    - [Cancellation](#cancellation)
    - [Progress and Completion Indication](#progress-and-completion-indication)
    - [SwingWorker](#swingworker)
  - [9.4. Shared Data Models](#94-shared-data-models)
    - [Thread-safe Data Models](#thread-safe-data-models)
    - [Split Data Models](#split-data-models)
  - [9.5. Other Forms of Single-threaded Subsystem](#95-other-forms-of-single-threaded-subsystem)

## 9.1. Why are GUIs Single-threaded?

옛부터 GUI Application들은 single-thread로 동작했고 events는 main event loop라는 곳에서 처리했다. 현대적인? GUI Application에서도 비슷하게 event를 처리할 때 event dispatch thread (EDT)를 사용한다.

GUI Application을 multi-thread로 하면 deadlock이 발생하기 쉬움. 예를 들어서 user가 마우스 클릭을 하면 os -> application로 event가 전달이 됨 (bubble-up). 반대로 배경화면을 바꾸는 등의 event를 application이 받으면 application -> os로 반대로 event가 전달이 됨 (bubble-down). 이런 역순 event 전달 케이스들 때문에 deadlock이 발생하기 쉬움.

### Sequential Event Processing

Event processing을 single thread로 하면 다른 event processing과의 간섭은 없으나 오래 걸리는 task가 있는 경우 다른 task들이 모두 늦게 실행될 수 있음.

### Thread Confinement in Swing

The Swing single-thread rule: Swing components and models should be created, modified, and queried only from the event-dispatching thread.

- Pros : synchronization 걱정할 필요 없음.
- Cons : event thread가 아니라면 presentation objects 에 접근 불가

```java
// SwingUtilities의 뇌피셜 구현. 아마 이런 식임.
public class SwingUtilities {
  private static final ExecutorService exec =
      Executors.newSingleThreadExecutor(new SwingThreadFactory());
  private static volatile Thread swingThread;

  private static class SwingThreadFactory implements ThreadFactory {
    public Thread newThread(Runnable r) {
      swingThread = new Thread(r);
      return swingThread;
    }
  }

  public static boolean isEventDispatchThread() {
    return Thread.currentThread() == swingThread;
  }

  public static void invokeLater(Runnable task) {
    // queue에 쌓음
    exec.execute(task);
  }

  public static void invokeAndWait(Runnable task)
      throws InterruptedException, InvocationTargetException {
    Future f = exec.submit(task);
    try {
      // wait
      f.get();
    } catch (ExecutionException e) {
      throw new InvocationTargetException(e);
    }
  }
}
```

```java
// SwingUtilities를 사용한 Single Threaded gui executor의 전형적인 구현
public class GuiExecutor extends AbstractExecutorService {
  // Singletons have a private constructor and a public factory
  private static final GuiExecutor instance = new GuiExecutor();

  private GuiExecutor() { }

  public static GuiExecutor instance() { return instance; }

  public void execute(Runnable r) {
    if (SwingUtilities.isEventDispatchThread()) {
      r.run();
    } else {
      SwingUtilities.invokeLater(r);
    }
  }

  // ... Plus trivial implementations of lifecycle methods
}
```

## 9.2. Short-running GUI Tasks

간단한 GUI task들은 event thread에서만 처리해도 괜찮음. 이 경우 confining presentation objects to the event thread is completely natural. (in this case, you can 
almost totally ignore threading concerns).

```java
// mouse event exmaple : mouse click -> action event -> action listener -> set color
final Random random = new Random();
final JButton button = new JButton("Change Color");
...
button.addActionListener(new ActionListener() {
  public void actionPerformed(ActionEvent e) {
    button.setBackground(new Color(random.nextInt()));
  }
});
```

## 9.3. Long-running GUI Tasks

Spell checking같은거 하는 긴 작업은 event thread가 아닌 다른 thread에서 이루어저야함.

```java
/* GUI에서 긴 작업을 event thread가 아닌 다른 thread에서 하는 예제 */
ExecutorService backgroundExec = Executors.newCachedThreadPool();
...
button.addActionListener(new ActionListener() {
  public void actionPerformed(ActionEvent e) {
    // set busy on ui
    button.setEnabled(false);
    label.setText("busy");
    // run in another thread
    backgroundExec.execute(new Runnable() {
      public void run() {
        try {
          doBigComputation();
        } finally {
          GuiExecutor.instance().execute(new Runnable() {
            public void run() {
              // set idle on ui
              button.setEnabled(true);
              label.setText("idle");
            }
          });
        }
      }
    });
  }
});
```

### Cancellation

GUI task가 너무 오래걸리면 취소도 할 수 있는게 좋음.

```java
/* GUI background task가 오래걸려서 사용자가 취소할 수 있는 예제 */
ExecutorService backgroundExec = Executors.newCachedThreadPool();
Future<?> runningTask = null;
...
startButton.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
  if (runningTask != null) {
    runningTask = backgroundExec.submit(new Runnable() {
      public void run() {
        while (moreWork()) {
          // if interrupt
          if (Thread.currentThread().isInterrupted()) {
            cleanUpPartialWork();
            break;
          }
          doSomeWork();
        }
      }
    });
  };
}});

cancelButton.addActionListener(new ActionListener() {
  public void actionPerformed(ActionEvent event) {
    if (runningTask != null) {
      // runningTask가 confined to event thread 이어서 별도의 sync필요 없음
      // true: mayInterruptIfRunning
      runningTask.cancel(true);
    }
  }
});
```

### Progress and Completion Indication

```java
// Completion + Process 추가한 클래스
abstract class BackgroundTask<V> implements Runnable, Future<V> {
  private final FutureTask<V> computation = new Computation();

  private class Computation extends FutureTask<V> {
    public Computation() {
      super(new Callable<V>() {
        public V call() throws Exception {
          return BackgroundTask.this.compute();
        }
      });
    }

    protected final void done() {
      GuiExecutor.instance().execute(new Runnable() {
        public void run() {
          V value = null;
          Throwable thrown = null;
          boolean cancelled = false;
          try {
            value = get();
          } catch (ExecutionException e) {
            thrown = e.getCause();
          } catch (CancellationException e) {
            cancelled = true;
          } catch (InterruptedException consumed) {
          } finally {
            onCompletion(value, thrown, cancelled);
          }
        };
      });
    }
  }

  protected void setProgress(final int current, final int max) {
    GuiExecutor.instance().execute(new Runnable() {
      public void run() {
        onProgress(current, max);
      }
    });
  }

  // Called in the background thread
  protected abstract V compute() throws Exception;

  // Called in the event thread, 사용자가 override 해서 사용
  protected void onCompletion(V result, Throwable exception, boolean cancelled) { }
  protected void onProgress(int current, int max) { }

  // ... Other Future methods forwarded to computation
}
```

```java
// Completion + Process 추가한 클래스 사용 예시
public void runInBackground(final Runnable task) {
  startButton.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
      // cancel button 클릭 시 작업을 취소하는 listener
      class CancelListener implements ActionListener {
        BackgroundTask<?> task;

        public void actionPerformed(ActionEvent event) {
          if (task != null) {
            task.cancel(true);
          }
        }
      }

      final CancelListener listener = new CancelListener();

      // 작업 생성
      listener.task = new BackgroundTask<Void>() {
        public Void compute() {
          // 실제 작업. 정상 종료시 onCompletion 호출
          while (moreWork() && !isCancelled()) {
            doSomeWork();
          }
          return null;
        }

        public void onCompletion(boolean cancelled, String s, Throwable exception) {
          // 완료 표시 및 cancelButton에서 listener 제거
          cancelButton.removeActionListener(listener);
          label.setText("done");
        }
      };

      cancelButton.addActionListener(listener);
      backgroundExec.execute(task);
    }
  });
}
```

### SwingWorker

앞서 소개한 오래걸리는 gui task를 다른 thread에서 실행하게 하고 취소할 수 있게 하는 기능은 다른 GUI application에도 적용할 수 있음. 앞서 구현한 것들은 Swing에서는 `SwingWorker`에서 제공해줌.

## 9.4. Shared Data Models

Swing에서는 event thread만 model을 변경할 수 있다. 그래서 main thread에서만 model을 변경하지 못하게 하면 문제 없음.

이처럼 shared data model에서는 multi-thread issue를 해결해야 함.

### Thread-safe Data Models

`ConcurrentHashMap` 처럼 fined-graned concurrency (bucket에만 lock을 걸음)를 제공하는 data structure를 사용하거나 `CopyOnWriteArrayList` 처럼 versioned data structure를 사용하면 multi thread에서 사용해도 문제 없음.

### Split Data Models

a presentation model and an application data model 를 분리시키는 것이 shared data model에서 하나의 대안이 될 수 있음.

- the presentation model : confined to the event thread 
- the application model : can be accessed by both the event thread and application threads

presentation model에서 application model에 listener를 달아서 application model이 변경되면 presentation model이 변경되는 식으로 동작. 변경되는 방식은 변경이 될 때 마다 snapshot을 떠서 update하거나 변경된 부분만 감지해서 update하는 방식이 있음. 당연히 변경된 부분만 감지하는 것이 더 좋음.

## 9.5. Other Forms of Single-threaded Subsystem

Thread Confinement는 UI뿐만 아니라 다른 곳에도 적용될 수 있음. 예를 들면 single thread로 만드는 것 외에는 synchronization이나 deadlock 문제를 해결할 수 없는 경우 쓸 수 있음. `System.loadLibrary`가 그 예시임.

UI에서 배운 개념을 바탕으로 Proxy같은거 써서 request를 intercept해서 thread confinement한 방법으로 처리할 수 있음. `Executor.newSingleThreadExecutor`같은거 쓰면 쉽게 함. Proxy를 써서 하면 Future에 submit하고 바로 get하는 식으로도 구현할 수 있음.