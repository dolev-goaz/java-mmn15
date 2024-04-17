package q1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelSummingManager {
    private static final int BATCH_COUNT = 2;

    private final int threadCount;
    private ExecutorService executor;
    private List<Integer> pool;
    public ParallelSummingManager(Integer[] dataSet, int threadCount) {
        this.threadCount = threadCount;
        executor = Executors.newFixedThreadPool(threadCount);
        pool = new ArrayList<>();
        Collections.addAll(pool, dataSet);
    }

    public int parallelSum() {
        for (int i = 0; i < threadCount; i++) {
            executor.execute(new SummationWorker(this));
        }
        executor.shutdown();
        while (!executor.isTerminated()) {} // wait for it to finish

        return pool.get(0);
    }

    public synchronized int[] getAndRemove() {
        if (pool.size() <= 1) {
            // nothing to sum
            throw new IndexOutOfBoundsException();
        }
        int count = Math.min(BATCH_COUNT, pool.size());
        int[] out = new int[count];
        for (int i = 0; i < count; i++) {
            out[i] = pool.remove(pool.size() - 1);
        }
        return out;
    }
    public synchronized void add(int item) {
        pool.add(item);
    }
}
