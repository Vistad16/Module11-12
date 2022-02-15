package javaCore.module11.task2;

import java.util.concurrent.atomic.AtomicInteger;

public class Fizzbuzz {

    private static final AtomicInteger counts = new AtomicInteger(1);

    public static volatile boolean fizzFlag = false; // % 3 == 0
    public static volatile boolean buzzFlag = false; // % 5 == 0
    public static volatile boolean fizzBuzzFlag = false; // % 3 == 0 && % 5 == 0

    public static void main(String[] args) {

        final Runnable fizz = () ->
        {
                while (true) {
                    if (fizzFlag) {
                        System.out.printf("[%s] fizz\n", Thread.currentThread().getName());
                        waitForOneSecond();
                        fizzFlag = false;
                    }
                }
        };

        final Runnable buzz = () ->
        {
                while (true) {
                    if (buzzFlag) {
                        System.out.printf("[%s] buzz\n", Thread.currentThread().getName());
                        waitForOneSecond();
                        buzzFlag = false;
                    }
                }
        };

        final Runnable fizzbuzz = () ->
        {
                while (true) {
                    if (fizzBuzzFlag) {
                        System.out.printf("[%s] fizzbuzz\n", Thread.currentThread().getName());
                        waitForOneSecond();
                        fizzBuzzFlag = false;
                    }
                }
        };

        final Runnable number = () -> {
            while (true) {
                final int count = counts.getAndIncrement(); //get and ++
                if (count % 3 == 0 && count % 5 == 0) {
                    fizzBuzzFlag = true;
                } else if (count % 3 == 0) {
                    fizzFlag = true;
                } else if (count % 5 == 0){
                    buzzFlag = true;
                } else {
                    System.out.printf("[%s] %d\n", Thread.currentThread().getName(), count);
                }
                waitForOneSecond();
            }
        };

        new Thread(fizz).start();
        new Thread(buzz).start();
        new Thread(fizzbuzz).start();
        new Thread(number).start();

    }

    private static void waitForOneSecond() {
        try {
            Thread.sleep(1_000);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }

}
