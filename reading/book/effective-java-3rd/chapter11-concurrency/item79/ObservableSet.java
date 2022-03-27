import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ObservableSet<E> extends ForwardingSet<E> {

    // Common

    public ObservableSet(Set<E> set) { super(set); }

    @Override public boolean add(E element) {
        boolean added = super.add(element);
        if (added)
            notifyElementAdded(element);
        return added;
    }

    @Override public boolean addAll(Collection<? extends E> c) {
        boolean result = false;
        for (E element : c)
            result |= add(element);  // Calls notifyElementAdded
        return result;
    }


    // Broken - invokes alien method from synchronized block! Over-synchronized!!

    private final List<SetObserver<E>> observers = new ArrayList<>();

    public void addObserver(SetObserver<E> observer) {
        synchronized(observers) {
            observers.add(observer);
        }
    }

    public boolean removeObserver(SetObserver<E> observer) {
        synchronized(observers) {
            return observers.remove(observer);
        }
    }

    private void notifyElementAdded(E element) {
        synchronized(observers) {
            // call alien inside synchronized block
            for (SetObserver<E> observer : observers)
                observer.added(this, element);
        }
    }


    // Solution1

    // Alien method moved outside of synchronized block - open calls

    // private void notifyElementAdded(E element) {
    //     List<SetObserver<E>> snapshot = null;
    //     synchronized(observers) {
    //         snapshot = new ArrayList<>(observers);
    //     }
    //     for (SetObserver<E> observer : snapshot)
    //         observer.added(this, element);
    // }


    // Solution2

    // Thread-safe observable set with CopyOnWriteArrayList
    // CopyOnWriteArrayList
    //   A variant of ArrayList in which all modification operations are
    //   implemented by making a fresh copy of the entire underlying array

    // private final List<SetObserver<E>> observers =
    //         new CopyOnWriteArrayList<>();

    // public void addObserver(SetObserver<E> observer) {
    //     observers.add(observer);
    // }

    // public boolean removeObserver(SetObserver<E> observer) {
    //     return observers.remove(observer);
    // }

    // private void notifyElementAdded(E element) {
    //     for (SetObserver<E> observer : observers)
    //         observer.added(this, element);
    // }
   
}
