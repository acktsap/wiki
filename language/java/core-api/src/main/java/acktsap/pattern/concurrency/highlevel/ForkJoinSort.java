/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.pattern.concurrency.highlevel;

import static java.util.Collections.shuffle;
import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ForkJoinSort {

    protected static int size = 100_000;

    interface Sort {

        void sort(int[] arr);
    }

    protected static class SerialMergeSort implements Sort {

        protected int[] temp = new int[size];

        public void sort(int[] arr) {
            sort(arr, 0, arr.length - 1);
        }

        protected void sort(int[] arr, int start, int end) {
            if (start < end) {
                int mid = (start + end) / 2;
                sort(arr, start, mid);
                sort(arr, mid + 1, end);
                merge(arr, start, mid, end);
            }
        }

        protected void merge(int[] arr, int start, int mid, int end) {
            int curr = start;
            int left = start;
            int right = mid + 1;
            while (left <= mid && right <= end) {
                if (arr[left] < arr[right]) {
                    temp[curr] = arr[left++];
                } else {
                    temp[curr] = arr[right++];
                }
                ++curr;
            }
            while (left <= mid) {
                temp[curr++] = arr[left++];
            }
            while (right <= end) {
                temp[curr++] = arr[right++];
            }
            System.arraycopy(temp, start, arr, start, end - start + 1);
        }

    }

    protected static class ParallelMergeSort implements Sort {

        protected final ForkJoinPool forkJoinPool =
            new ForkJoinPool(Runtime.getRuntime().availableProcessors());

        protected final int[] temp = new int[size];

        public void sort(int[] arr) {
            try {
                forkJoinPool.submit(new SortAction(temp, arr, 0, arr.length - 1));
                forkJoinPool.awaitTermination(5000L, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
            }
        }

        protected class SortAction extends RecursiveAction {

            private static final long serialVersionUID = 4204219456794856464L;

            protected final int threshold = 1;

            protected final int[] temp;
            protected final int[] arr;
            protected final int start;
            protected final int end;

            protected SortAction(int[] temp, int[] arr, int start, int end) {
                this.temp = temp;
                this.arr = arr;
                this.start = start;
                this.end = end;
            }

            @Override
            protected void compute() {
                int mid = (start + end) / 2;
                if (threshold < (end - start + 1)) {
                    SortAction left = new SortAction(temp, arr, start, mid);
                    SortAction right = new SortAction(temp, arr, mid + 1, end);
                    invokeAll(left, right);
                }
                merge(arr, start, mid, end);
            }

            protected void merge(int[] arr, int start, int mid, int end) {
                int curr = start;
                int left = start;
                int right = mid + 1;
                while (left <= mid && right <= end) {
                    if (arr[left] < arr[right]) {
                        temp[curr] = arr[left++];
                    } else {
                        temp[curr] = arr[right++];
                    }
                    ++curr;
                }
                while (left <= mid) {
                    temp[curr++] = arr[left++];
                }
                while (right <= end) {
                    temp[curr++] = arr[right++];
                }
                System.arraycopy(temp, start, arr, start, end - start + 1);
            }

        }

    }

    protected static void run(final Sort sort) {
        List<Integer> list = IntStream.range(0, size).mapToObj(Integer::new).collect(toList());
        ;
        int[] expected = list.stream().mapToInt(i -> i).toArray();
        shuffle(list);
        int[] input = list.stream().mapToInt(i -> i).toArray();

        long start = System.nanoTime() / 1_000_000;
        sort.sort(input);
        long end = System.nanoTime() / 1_000_000;
        if (!Arrays.equals(expected, input)) {
            throw new IllegalStateException("expected: " + Arrays.toString(expected) +
                ", actual: " + Arrays.toString(input) + "(Algorithm: " + sort.getClass() + ")");
        }
        System.out.printf("Elapsed time by %s: %dms %n", sort.getClass(), end - start);
    }

    public static void main(String[] args) {
        Sort serial = new SerialMergeSort();
        Sort parallel = new ParallelMergeSort();
        run(serial);
        run(parallel);
    }

}
