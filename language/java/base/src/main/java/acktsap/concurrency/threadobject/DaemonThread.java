/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.concurrency.threadobject;

public class DaemonThread implements Runnable {

    static boolean autoSave = false;

    // 데몬 스레드는 다른 일반 스레드의 작업을 돕는 보조적인 역할 수행 일반 스레드가 모두 종료되면
    // 데몬 스레드는 강제적으로 자동 종료. 그 이유는 데몬 스레드는 일반 스레드 보조역할을 수행하므로
    // 일반 스레드 종료시 존재 의미 없기 떄문이다. 데몬스레드 예시 : 가비지 컬렉터, 워드프로세서 자동저장, 화면자동갱신 등..
    public static void main(String[] args) {

        /**
         * 데몬 스레드 생성법은 일반 스레드 생성과 똑같은데 setDaemon만 true로 해주면 된다.
         *
         * 데몬 스레드는 무한루프와 조건문을 이용해서 실행 후 대기하다가 특정 조건 만족되면 작업 수행하고 다시 대기하도록 작성한다.
         */
        Thread t = new Thread(new DaemonThread());
        t.setDaemon(true); // 이부분이 중요! 없을 경우 종료되지 않음
        t.start();

        for (int i = 1; i <= 10; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(i);

            if (i == 5)
                autoSave = true;
        }

        System.out.println("프로그램을 종료합니다.");

    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(300); // every 3 ms
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (autoSave) {
                autoSave();
            }
        }
    }

    public void autoSave() {
        System.out.println("작업파일이 자동저장되었습니다.");
    }

}
