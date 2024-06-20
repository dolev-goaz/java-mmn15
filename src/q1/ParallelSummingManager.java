package q1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// class representing a manager for parallel summing
public class ParallelSummingManager {

    // batch size for each summation
    private static final int BATCH_SIZE = 2;

    private final ExecutorService executor;
    private final List<Integer> pool;

    // constructor
    public ParallelSummingManager(Integer[] dataSet, int threadCount) {
        executor = Executors.newFixedThreadPool(threadCount);
        pool = new ArrayList<>();
        Collections.addAll(pool, dataSet);
    }

    // create all the summation workers to run in parallel
    public int parallelSum() {
        // each summation we remove BATCH_SIZE items and add 1 item. so each summation removes (BATCH_SIZE - 1) items.
        // we finish when there's 1 item left in the pool. so we in total remove (pool.size() - 1) items.
        // so the total amount of steps is the division of the two, rounded up.
        final double amountOfAdditions = Math.ceil((pool.size() - 1.0) / (BATCH_SIZE - 1.0));

        for (int i = 0; i < amountOfAdditions; i++) {
            executor.execute(new SummationWorker(this));
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return pool.get(0);
    }

    // returns a batch from the pool to a summation worker
    public synchronized int[] getAndRemove() {
        if (pool.size() <= 1) {
            // nothing to sum- only the result(at most) remains in the pool
            throw new IndexOutOfBoundsException();
        }

        // clamp batch size so if there aren't enough items, return the existing amount
        int count = Math.min(BATCH_SIZE, pool.size());

        // build the returned items array
        int[] out = new int[count];
        for (int i = 0; i < count; i++) {
            out[i] = pool.remove(pool.size() - 1);
        }

        // return the result
        return out;
    }

    // adds an item back to the pool
    public synchronized void add(int item) {
        pool.add(item);
    }
}
