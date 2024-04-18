package q2;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// class representing an airport and handling runways
public class Airport {

    private final String name;
    private final List<Integer> availableRunways;
    private final Queue<Integer> requestOrder;

    // constructor
    public Airport(String name, int runwayCount) {
        this.name = name;
        this.availableRunways = new ArrayList<Integer>();
        for (int i = 0; i < runwayCount; i++) {
            this.availableRunways.add(i);
        }
        requestOrder = new ArrayDeque<Integer>();
    }

    // wait for an empty runway(while maintaining order) and returns the runway the flight could depart from
    public int depart(int flightNo) {
        int runway = occupyRunway(flightNo);
        System.out.println(String.format("Flight #%d is departing from runway %d in airport %s", flightNo, runway, name));
        return runway;
    }

    // wait for an empty runway(while maintaining order) and returns the runway the flight could land on.
    public int land(int flightNo) {
        int runway = occupyRunway(flightNo);
        System.out.println(String.format("Flight #%d is landing in runway %d in airport %s", flightNo, runway, name));
        return runway;
    }

    // frees a runway, allowing other planes to use it.
    public synchronized void freeRunway(int flightNo, int runwayNo) {
        availableRunways.add(runwayNo);
        notifyAll();
    }

    // occupies a runway, used for both depart and land.
    private synchronized int occupyRunway(int flightNo) {
        waitForTurn(flightNo);
        return availableRunways.remove(availableRunways.size() - 1); // remove from the end
    }

    // a synchronized method that waits until the flight has an available runway, while maintaining order of requests-
    // the flight that requested a runway first will get a runway first.
    private synchronized void waitForTurn(int flightNo) {
        if (availableRunways.size() != 0) {
            // no reason to wait
            return;
        }
        System.out.println(String.format("Flight #%d is waiting for an empty runway in airport %s", flightNo, name));
        // add to the end of the queue
        requestOrder.add(flightNo);

        // wait for the flight's turn with the runways
        while ((availableRunways.size() == 0) || (requestOrder.peek() != flightNo)) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace(); // NOTE: could be empty catch-clause here
            }
        }
        /* after the while loop, there's an empty runway AND its our turn. now we can remove the current flight from
        the queue */
        requestOrder.remove();
    }

}
