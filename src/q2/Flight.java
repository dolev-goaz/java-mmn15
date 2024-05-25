package q2;

import java.util.Random;

// Class representing a flight, extends thread
public class Flight extends Thread {
    private static final int SEC_TO_MS = 1000;
    private static final int MIN_RANDOM_TIME = 2;
    private static final int MAX_RANDOM_TIME = 5;
    private final Airport source, target;
    private final int flightNo;

    // constructor
    public Flight(int flightNo, Airport source, Airport target) {
        this.source = source;
        this.target = target;
        this.flightNo = flightNo;
    }

    @Override
    // running the thread(flight)
    public void run() {
        super.run();

        takeoff();
        fly();
        land();
    }

    // flight take off
    private void takeoff() {
        int runway = source.depart(flightNo);
        try {
            waitRandomTime();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            source.freeRunway(flightNo, runway);
        }
    }

    // flight in-air
    private void fly() {
        try {
            waitRandomTime();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // flight landing
    private void land() {
        int runway = target.land(flightNo);
        try {
            waitRandomTime();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            target.freeRunway(flightNo, runway);
        }
    }

    // waits for a random amount of time
    private void waitRandomTime() throws InterruptedException {
        Thread.sleep(generateRandomTimeMS());
    }

    // generates a random amount of time in milliseconds
    private int generateRandomTimeMS() {
        return random(MIN_RANDOM_TIME, MAX_RANDOM_TIME) * SEC_TO_MS;
    }

    // returns a random number between min(inclusive) and max(exclusive)
    private int random(int min, int max) {
        return new Random().nextInt(max-min) + min;
    }
}
