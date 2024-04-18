package q2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Airport {

    private final String name;
    private final Lock lock;
    private boolean[] runwayTakenStatus;

    public Airport(String name, int runwayCount) {
        this.name = name;
        this.runwayTakenStatus = new boolean[runwayCount];
        for (int i = 0; i < runwayCount; i++) {
            this.runwayTakenStatus[i] = false;
        }
        lock = new ReentrantLock();
    }

    public int depart(int flightNo) {
        return -1;
    }

    public int land(int flightNo) {
        return -1;
    }

    public void freeRunway(int flightNo, int runwayNo) {

    }

}
