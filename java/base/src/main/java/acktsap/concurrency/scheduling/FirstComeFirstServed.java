package acktsap.concurrency.scheduling;

import acktsap.Block;

import java.time.LocalTime;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class FirstComeFirstServed {
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
        Block.dt("FCFS Best case", () -> {
            Queue<Task> queue = new LinkedBlockingQueue<>();
            queue.add(new Task(1.0));
            queue.add(new Task(2.0));
            queue.add(new Task(3.0));

            while (!queue.isEmpty()) {
                Task task = queue.poll();
                task.run();
                System.out.println(task + " finished (" + LocalTime.now() + ")");
            }
        });

        Block.dt("FCFS Bad case", () -> {
            Queue<Task> queue = new LinkedBlockingQueue<>();
            queue.add(new Task(3.0));
            queue.add(new Task(2.0));
            queue.add(new Task(1.0));

            while (!queue.isEmpty()) {
                Task task = queue.poll();
                task.run();
                System.out.println(task + " finished (" + LocalTime.now() + ")");
            }
        });
    }
}
