package q2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Airport {

    private final String name;
    private final Lock lock;
    List<Integer> availableRunways;

    public Airport(String name, int runwayCount) {
        this.name = name;
        this.availableRunways = new ArrayList<Integer>();
        for (int i = 0; i < runwayCount; i++) {
            this.availableRunways.add(i);
        }
        this.lock = new ReentrantLock();
    }

    public int depart(int flightNo) {
        int runway = occupyRunway(flightNo);
        System.out.println(String.format("Flight #%d is departing from runway %d in airport %s", flightNo, runway, name));
        return runway;
    }

    public int land(int flightNo) {
        int runway = occupyRunway(flightNo);
        System.out.println(String.format("Flight #%d is landing in runway %d in airport %s", flightNo, runway, name));
        return runway;
    }

    public synchronized void freeRunway(int flightNo, int runwayNo) {
        availableRunways.add(runwayNo);
        notify(); // its enough to notify a single flight since only one runway is freed
    }

    private synchronized int occupyRunway(int flightNo) {
        while (availableRunways.size() == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return availableRunways.remove(availableRunways.size() - 1); // remove from the end
    }

}
