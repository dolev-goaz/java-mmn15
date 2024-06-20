package q1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// class representing a manager for parallel summing
public class ParallelSummingManager {

    // amount returned for each summation
    private static final int BATCH_COUNT = 2;

    private final int threadCount;
    private final ExecutorService executor;
    private final List<Integer> pool;

    // constructor
    public ParallelSummingManager(Integer[] dataSet, int threadCount) {
        this.threadCount = threadCount;
        executor = Executors.newFixedThreadPool(threadCount);
        pool = new ArrayList<>();
        Collections.addAll(pool, dataSet);
    }

    // create all the summation workers to run in parallel
    public int parallelSum() {
        for (int i = 0; i < threadCount; i++) {
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
        int count = Math.min(BATCH_COUNT, pool.size());
        int[] out = new int[count];
        for (int i = 0; i < count; i++) {
            out[i] = pool.remove(pool.size() - 1);
        }
        return out;
    }

    // adds an item back to the pool
    public synchronized void add(int item) {
        pool.add(item);
    }
}
