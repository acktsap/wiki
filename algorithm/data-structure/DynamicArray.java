import java.lang.IndexOutOfBoundsException;

public class DynamicArray {

  interface IDynamicArray<T> {
    T get(int index);
    T set(int index, T element);
    T add(T element);
    boolean remove(T element);
    int size();
  }

  static class DynamicArrayImpl<T> implements IDynamicArray<T> {
    protected Object[] datas = {};
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

    }

    @Override
    public T add(T element) {

    }

    @Override
    public boolean remove(T element) {
      int found = -1;
      for (int i = 0; i < size; ++i) {
        if (this.datas[i] == element) {
          found = i;
          break;
        }
      }
      return found != -1;
    }

    @Override
    public int size() {
      return this.size;
    }
  }

  public static void main(String[] args) {

  }

}
