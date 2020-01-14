import java.util.Arrays;
import java.lang.IndexOutOfBoundsException;

public class LinkedListTest {

  interface ILinkedList<T> {
    T get(int index);
    T set(int index, T element);
    boolean add(T element);
    boolean remove(T element);
    int size();
  }

  static class LinkedListImpl<T> implements ILinkedList<T> {
    protected Node<T> head = null;
    protected Node<T> tail = null;
    protected int size = 0;

    @Override
    public T get(int index) {
      if (this.size <= index) {
        throw new IndexOutOfBoundsException();
      }

      Node<T> curr = head;
      int i = 0;
      while (i < index) {
        curr = curr.next;
        ++i;
      }

      return curr.value;
    }


    @Override
    public T set(int index, T element) {
      if (this.size <= index) {
        throw new IndexOutOfBoundsException();
      }

      Node<T> curr = head;
      int i = 0;
      while (i < index) {
        curr = curr.next;
        ++i;
      }
      final T oldValue = curr.value;
      curr.value = element;

      return oldValue;
    }

    public boolean add(T element) {
      final Node<T> newTail = new Node<T>();
      newTail.value = element;

      if (null == this.head) {
        this.head = newTail;
        this.tail = newTail;
      } else {
        // oldtall <- newtail
        newTail.pre = this.tail;
        // oldtall -> newtail
        this.tail.next = newTail;
        // tail -> newtail
        this.tail = newTail;
      }
      ++size;

      return true;
    }

    public boolean remove(T element) {
      Node curr = head;
      while (null != curr && curr.value != element) {
        curr = curr.next;
      }
      if (null != curr) {
        Node<T> pre = curr.pre;
        Node<T> next = curr.next;
        pre.next = next;
        --size;
        return true;
      }
      return false;
    }

    @Override
    public int size() {
      return this.size;
    }

    private class Node<T> {
      T value;
      Node<T> pre;
      Node<T> next;
    }
  }

  public static void main(String[] args) {
    final ILinkedList<Integer> linkedList = new LinkedListImpl();

    // add
    linkedList.add(0);
    linkedList.add(1);
    linkedList.add(2);
    linkedList.add(3);
    linkedList.add(4);
    linkedList.add(5);
    assert linkedList.size() == 6;
    assert linkedList.get(4) == 4;

    // set
    linkedList.set(1, 100);
    assert linkedList.get(1) == 100;

    // remove
    assert true == linkedList.remove(3);
    assert false == linkedList.remove(999);
    assert linkedList.size() == 5;
    assert linkedList.get(3) == 4;
    
    // add
    linkedList.add(100);
    linkedList.add(200);
    linkedList.add(300);
    linkedList.add(400);
    assert linkedList.size() == 9;
    assert linkedList.get(5) == 100;
  }

}
