/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.snippet.concurrency.threadobject;

/**
 * @author taeiklim
 */
public class ThreadPriority {

    // Priority방식과 Round-Robin방식을 사용.
    // Round-Robin때문에 둘다 실행되어서 Priority가 낮아도 실행됨
    // Priority : Run high priority first
    // Round-Robin : time-slice amoung thread
    public static void main(String[] args) {
        // 이상해 10이 제일 먼저 끝나야 할거 같은데 아님
        // Round-Robin때문거같음
        for (int i = 1; i <= 10; ++i) {
            Thread thread = new Thread(() -> {
                for (long j = 0; j < 3_000_000_000L; ++j) ;
                System.out.println(Thread.currentThread().getName());
            });
            thread.setName("Thread-" + i);
            if (i == 10) {
                thread.setPriority(10);
            } else {
                thread.setPriority(1);
            }
            thread.start();
        }

    }

}
