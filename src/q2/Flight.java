package q2;

import java.util.Random;

public class Flight extends Thread {
    private final Airport source, target;
    private final int flightNo;

    public Flight(int flightNo, Airport source, Airport target) {
        this.source = source;
        this.target = target;
        this.flightNo = flightNo;
    }

    @Override
    public void run() {
        super.run();

        takeoff();
        fly();
        land();
    }

    private void takeoff() {
        int runway = source.depart(flightNo);
        try {
            Thread.sleep(randomDurationTakeoffLandingMS());
        } catch (InterruptedException ignored) {
        } finally {
            source.freeRunway(flightNo, runway);
        }
    }

    private void fly() {
        try {
            Thread.sleep(randomDurationFlightMS());
        } catch (InterruptedException e) {
        }
    }

    private void land() {
        int runway = target.land(flightNo);
        try {
            Thread.sleep(randomDurationTakeoffLandingMS());
        } catch (InterruptedException ignored) {
        } finally {
            target.freeRunway(flightNo, runway);
        }
    }

    private int randomDurationFlightMS() {
        return random(2, 5) * 1000;
    }

    private int randomDurationTakeoffLandingMS() {
        return random(2, 5) * 1000;
    }

    private int random(int min, int max) {
        return new Random().nextInt(max-min) + min;
    }
}
