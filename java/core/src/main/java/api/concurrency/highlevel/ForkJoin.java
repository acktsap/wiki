/*
 * @copyright defined in LICENSE.txt
 */

package api.concurrency.highlevel;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ForkJoin {

  // Wrap this code in a ForkJoinTask subclass, typically using one of its more specialized types,
  // either RecursiveTask (which can return a result) or RecursiveAction.
  protected static class ForkJoinAction extends RecursiveAction {

    private static final long serialVersionUID = 1L;

    protected static final int sThreshold = 100000;

    private int[] mSource;
    private int mStart;
    private int mLength;
    private int[] mDestination;

    // Processing window size; should be odd.
    private int mBlurWidth = 15;

    public ForkJoinAction(int[] src, int start, int length, int[] dst) {
      mSource = src;
      mStart = start;
      mLength = length;
      mDestination = dst;
    }

    protected void computeDirectly() {
      int sidePixels = (mBlurWidth - 1) / 2;
      for (int index = mStart; index < mStart + mLength; index++) {
        // Calculate average.
        float rt = 0, gt = 0, bt = 0;
        for (int mi = -sidePixels; mi <= sidePixels; mi++) {
          int mindex = Math.min(Math.max(mi + index, 0),
              mSource.length - 1);
          int pixel = mSource[mindex];
          rt += (float) ((pixel & 0x00ff0000) >> 16)
              / mBlurWidth;
          gt += (float) ((pixel & 0x0000ff00) >> 8)
              / mBlurWidth;
          bt += (float) ((pixel & 0x000000ff) >> 0)
              / mBlurWidth;
        }

        // Reassemble destination pixel.
        int dpixel = (0xff000000) |
            (((int) rt) << 16) |
            (((int) gt) << 8) |
            (((int) bt) << 0);
        mDestination[index] = dpixel;
      }
      System.out.format("[Thread: %s] Compute terminated %n", Thread.currentThread());
    }

    protected void compute() {
      if (mLength < sThreshold) {
        computeDirectly();
        return;
      }

      int split = mLength / 2;

      invokeAll(new ForkJoinAction(mSource, mStart, split, mDestination),
          new ForkJoinAction(mSource, mStart + split, mLength - split,
              mDestination));
    }
  }

  // One such implementation, introduced in Java SE 8, is used by the java.util.Arrays class for its
  // parallelSort() methods. These methods are similar to sort(), but leverage concurrency via the
  // fork/join framework.
  // Another implementation of the fork/join framework is used by methods in the java.util.streams
  // package, which is part of Project Lambda scheduled for the Java SE 8 release
  public static void main(String[] args) {
    final ForkJoinPool forkJoinPool = new ForkJoinPool();
    final int[] src = new int[0];
    final int[] destination = new int[0];
    forkJoinPool.invoke(new ForkJoinAction(src, 0, src.length, destination));
  }

}
