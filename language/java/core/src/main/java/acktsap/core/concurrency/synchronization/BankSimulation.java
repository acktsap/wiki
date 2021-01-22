/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.core.concurrency.synchronization;

public class BankSimulation {

    protected static class Bank {

        protected int amount = 0;

        protected boolean filled = false;

        public synchronized void deposit(final int amount) {
            while (filled) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }

            this.amount = amount;
            this.filled = true;

            System.out.printf("[Bank - %s] - deposit: %d%n", Thread.currentThread(),
                amount);
            notifyAll();
        }

        public synchronized int withdraw() {
            while (!filled) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }

            final int ret = this.amount;
            this.amount = 0;
            this.filled = false;

            System.out.printf("[Bank - %s] - Withdraw: %d%n", Thread.currentThread(), ret);
            notifyAll();

            return ret;
        }

    }

    protected static class Depositor implements Runnable {

        protected final Bank bank;

        public Depositor(final Bank bank) {
            this.bank = bank;
        }

        @Override
        public void run() {
            final int amount = 10;
            int totalAmount = 0;
            while (totalAmount < 100) {
                bank.deposit(amount);
                totalAmount += amount;
            }
            System.out.printf("[%s] done%n", Thread.currentThread());
        }

    }

    protected static class WithDrawer implements Runnable {

        protected final Bank bank;

        protected int amount = 0;

        public WithDrawer(final Bank bank) {
            this.bank = bank;
        }

        @Override
        public void run() {
            while (amount < 100) {
                final int withdrawed = bank.withdraw();
                amount += withdrawed;
            }
            System.out.printf("[%s] done%n", Thread.currentThread());
        }

    }

    public static void main(String[] args) {
        final Bank bank = new Bank();
        new Thread(new Depositor(bank)).start();
        new Thread(new WithDrawer(bank)).start();
    }

}
