package javaCore.module11.task1;

import java.util.concurrent.atomic.AtomicInteger;
public class SessionTime {

    private static final AtomicInteger second = new AtomicInteger(1);

    private static volatile boolean flag = false;//switcher

    public static void main(final String[] args) {
        final Runnable secondsFromStartRunnable = () -> {
            while (true) {
                final int seconds = second.getAndIncrement(); //get and ++
                System.out.printf("[%s] From session start %d seconds pass\n", Thread.currentThread().getName(), seconds);
                if (seconds % 5 == 0) {
                    flag = true;
                }
                waitForOneSecond();
            }
        };

        final Runnable everyFiveSecondMessage = () -> {
            while (true) {
                if (flag) {
                    System.out.printf("[%s] Five seconds pass\n", Thread.currentThread().getName());
                    flag = false;
                }
            }
        };
        //after start wait 1 second before output first second
        System.out.println("Start session");
        waitForOneSecond();
        new Thread(secondsFromStartRunnable).start();
        new Thread(everyFiveSecondMessage).start();
    }

    private static void waitForOneSecond() {
        try {
            Thread.sleep(1_000);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }
}
