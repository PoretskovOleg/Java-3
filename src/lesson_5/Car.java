package lesson_5;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Car implements Runnable {

    private static int CARS_COUNT;
    private static boolean winner;
    private final CyclicBarrier prepare;
    private final CountDownLatch start;
    private final CountDownLatch finish;
    private Race race;
    private int speed;
    private String name;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed, CyclicBarrier prepare, CountDownLatch start, CountDownLatch finish) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        this.prepare = prepare;
        this.start = start;
        this.finish = finish;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
            start.countDown();
            Thread.sleep(100);
            prepare.await();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }

        if (!winner) {
            winner = true;
            System.out.println(this.name + " - WIN!!!!!");
        }
        finish.countDown();
    }
}