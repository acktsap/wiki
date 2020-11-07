/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.core.concurrency.highlevel;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockObject {

    protected static class Friend {

        private final String name;
        private final Lock lock = new ReentrantLock();

        public Friend(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        // We model this improvement by requiring that our Friend objects must acquire locks for both
        // participants before proceeding with the bow
        public boolean impendingBow(Friend bower) {
            Boolean myLock = false;
            Boolean yourLock = false;
            try {
                myLock = lock.tryLock();
                yourLock = bower.lock.tryLock();

                // Can be like
                //
                // lock.tryLock(3000L, TimeUnit.MILLISECONDS);
                // bower.lock.tryLock(3000L, TimeUnit.MILLISECONDS);
                //
                // The biggest advantage of Lock objects over implicit locks is their ability to back out of
                // an attempt to acquire a lock. The tryLock method backs out if the lock is not available
                // immediately or before a timeout expires (if specified)

            } finally {
                if (!(myLock && yourLock)) {
                    // if not both bowed, release lock
                    if (myLock) {
                        lock.unlock();
                    }
                    if (yourLock) {
                        bower.lock.unlock();
                    }
                }
            }
            return myLock && yourLock;
        }

        public void bow(Friend bower) {
            if (impendingBow(bower)) {
                try {
                    System.out.format("%s: %s has"
                            + " bowed to me!%n",
                        this.name, bower.getName());
                    bower.bowBack(this);
                } finally {
                    lock.unlock();
                    bower.lock.unlock();
                }
            } else {
                System.out.format("%s: %s started"
                        + " to bow to me, but saw that"
                        + " I was already bowing to"
                        + " him.%n",
                    this.name, bower.getName());
            }
        }

        public void bowBack(Friend bower) {
            System.out.format("%s: %s has" +
                    " bowed back to me!%n",
                this.name, bower.getName());
        }
    }

    protected static class BowLoop implements Runnable {

        private Friend bower;
        private Friend bowee;

        public BowLoop(Friend bower, Friend bowee) {
            this.bower = bower;
            this.bowee = bowee;
        }

        public void run() {
            Random random = new Random();
            while (true) {
                try {
                    Thread.sleep(random.nextInt(10));
                } catch (InterruptedException e) {
                }
                bowee.bow(bower);
            }
        }
    }

    // Lock objects work very much like the implicit locks used by synchronized code. As with implicit
    // locks, only one thread can own a Lock object at a time. Lock objects also support a wait/notify
    // mechanism, through their associated Condition objects.
    public static void main(String[] args) {
        final Friend alphonse = new Friend("Alphonse");
        final Friend gaston = new Friend("Gaston");
        new Thread(new BowLoop(alphonse, gaston)).start();
        new Thread(new BowLoop(gaston, alphonse)).start();
    }

}
