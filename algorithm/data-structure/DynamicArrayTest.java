import java.util.Arrays;
import java.lang.IndexOutOfBoundsException;

public class DynamicArrayTest {

  interface IDynamicArray<T> {
    T get(int index);
    T set(int index, T element);
    boolean add(T element);
    boolean remove(T element);
    int size();
  }

  static class DynamicArrayImpl<T> implements IDynamicArray<T> {
    protected static int INITIAL_CAPACITY = 5;
    protected Object[] datas = new Object[INITIAL_CAPACITY];
    protected int size = 0;

    @Override
    public T get(int index) {
      if (this.size <= index) {
        throw new IndexOutOfBoundsException();
      }
      return (T) this.datas[index];
    }

    @Override
    public T set(int index, T element) {
      if (this.size <= index) {
        throw new IndexOutOfBoundsException();
      }
      final T oldValue = (T) this.datas[index];
      this.datas[index] = element;
      return oldValue;
    }

    @Override
    public boolean add(T element) {
      if (this.size == this.datas.length) {
        this.datas = Arrays.copyOf(this.datas, this.size + (this.size >> 1));
      }
      this.datas[size] = element;
      ++size;
      return true;
    }

    @Override
    public boolean remove(T element) {
      int foundIndex = -1;
      for (int i = 0; i < size; ++i) {
        if (this.datas[i] == element) {
          foundIndex = i;
          break;
        }
      }

      if (-1 != foundIndex) {
        int countBeforefoundIndex = foundIndex + 1;
        System.arraycopy(this.datas, foundIndex + 1, this.datas, foundIndex, (this.size - countBeforefoundIndex));
        --size;
      }

      return foundIndex != -1;
    }

    @Override
    public int size() {
      return this.size;
    }
  }

  public static void main(String[] args) {
    final IDynamicArray<Integer> dynamicArray = new DynamicArrayImpl();

    // add
    dynamicArray.add(0);
    dynamicArray.add(1);
    dynamicArray.add(2);
    dynamicArray.add(3);
    dynamicArray.add(4);
    dynamicArray.add(5);
    assert dynamicArray.size() == 6;
    assert dynamicArray.get(4) == 4;

    // set
    dynamicArray.set(1, 100);
    assert dynamicArray.get(1) == 100;

    // remove
    assert true == dynamicArray.remove(3);
    assert false == dynamicArray.remove(999);
    assert dynamicArray.size() == 5;
    assert dynamicArray.get(3) == 4;
    
    // add
    dynamicArray.add(100);
    dynamicArray.add(200);
    dynamicArray.add(300);
    dynamicArray.add(400);
    assert dynamicArray.size() == 9;
    assert dynamicArray.get(5) == 100;
  }

}
