package acktsap.concurrency.scheduling;

import acktsap.Block;

import java.util.LinkedList;
import java.util.Queue;

public class RoundRobin {
    static class Task {
        private int id;
        private int time;

        public Task(int remaining) {
            this.id = remaining;
            this.time = remaining;
        }

        public void action(int time) {
            this.time -= time;
            if (this.time < 0) {
                this.time = 0;
            }
        }

        @Override
        public String toString() {
            return "Task{" +
                "id=" + id +
                ", time=" + time +
                '}';
        }
    }

    public static void main(String[] args) {
        Block.dt("RoundRobin", () -> {
            Queue<Task> queue = new LinkedList<>();

            queue.add(new Task(1));
            queue.add(new Task(2));
            queue.add(new Task(4));
            queue.add(new Task(5));
            queue.add(new Task(6));
            queue.add(new Task(7));

            while (!queue.isEmpty()) {
                Task task = queue.poll();
                task.action(1);
                if (task.time > 0) {
                    queue.add(task);
                }
                System.out.printf("remaining set: %s%n", queue);
            }
        });

        Block.dt("RoundRobin with big quantum (same as FCFS)", () -> {
            Queue<Task> queue = new LinkedList<>();

            queue.add(new Task(1));
            queue.add(new Task(2));
            queue.add(new Task(4));
            queue.add(new Task(5));
            queue.add(new Task(6));
            queue.add(new Task(7));

            while (!queue.isEmpty()) {
                Task task = queue.poll();
                task.action(10); // big
                if (task.time > 0) {
                    queue.add(task);
                }
                System.out.printf("remaining set: %s%n", queue);
            }
        });
    }
}
