package acktsap.concurrency.scheduling;

import acktsap.Block;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;

public class Priority {
    static class Task implements Runnable {
        private final double runnningTime;
        private final int priority;
        private int age;

        public Task(double runnningTime, int priority) {
            this(runnningTime, priority, 0);
        }

        public Task(double runnningTime, int priority, int age) {
            this.runnningTime = runnningTime;
            this.priority = priority;
            this.age = age;
        }

        public void increaseAge() {
            ++age;
        }

        public boolean isOld() {
            return 5 < age;
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
                ", priority=" + priority +
                ", age=" + age +
                '}';
        }
    }

    public static void main(String[] args) {
        Block.dt("Priority", () -> {
            Queue<Task> highestQueue = new PriorityQueue<>((l, r) -> Double.compare(l.priority, r.priority));
            Queue<Task> queue = new PriorityQueue<>((l, r) -> Double.compare(l.priority, r.priority));

            queue.add(new Task(2.1, 5, 3));
            queue.add(new Task(2.2, 5, 3));
            queue.add(new Task(1.1, 1));
            queue.add(new Task(1.2, 2));
            queue.add(new Task(1.3, 3));
            queue.add(new Task(1.4, 4));
            queue.add(new Task(1.5, 5));
            queue.add(new Task(1.6, 6));
            queue.add(new Task(1.7, 7));
            queue.add(new Task(1.8, 8));

            while (!queue.isEmpty()) {
                while (!highestQueue.isEmpty()) {
                    Task task = highestQueue.poll();
                    task.run();
                    System.out.printf("%s finished from priority one%n", task);
                }
                Task task = queue.poll();
                task.run();

                queue.forEach(Task::increaseAge);

                List<Task> old = queue.stream()
                    .filter(Task::isOld)
                    .collect(Collectors.toList());
                queue.removeAll(old);
                highestQueue.addAll(old);

                System.out.printf("%s finished (remaining set: %s)%n", task, queue);
            }

            // 남은거 처리
            while (!highestQueue.isEmpty()) {
                Task task = highestQueue.poll();
                task.run();
                System.out.printf("%s finished from priority one%n", task);
            }
        });
    }
}
