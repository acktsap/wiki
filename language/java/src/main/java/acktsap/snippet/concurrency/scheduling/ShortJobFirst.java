package acktsap.snippet.concurrency.scheduling;

import acktsap.Block;

import java.time.LocalTime;
import java.util.PriorityQueue;
import java.util.Queue;

public class ShortJobFirst {
    static class Task implements Runnable {
        private final double runnningTime;

        public Task(double runnningTime) {
            this.runnningTime = runnningTime;
        }

        @Override
        public void run() {
            try {
                Thread.sleep((long) (runnningTime * 1000d));
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }

        @Override
        public String toString() {
            return "Task{" +
                "runnningTime=" + runnningTime +
                '}';
        }
    }

    public static void main(String[] args) {
        Block.dt("SJF", () -> {
            Queue<Task> queue = new PriorityQueue<>((l, r) -> Double.compare(l.runnningTime, r.runnningTime));
            queue.add(new Task(3)); // 마지막에 실행
            queue.add(new Task(2));
            queue.add(new Task(1.3));
            queue.add(new Task(1.2));
            queue.add(new Task(1.1));

            while (!queue.isEmpty()) {
                Task task = queue.poll();
                task.run();
                System.out.println(task + " finished (" + LocalTime.now() + ")");
            }
        });
    }
}
