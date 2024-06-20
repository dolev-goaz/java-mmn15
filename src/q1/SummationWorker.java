package q1;

public class SummationWorker extends Thread {
    private final ParallelSummingManager manager;
    public SummationWorker(ParallelSummingManager m) {
        super();
        manager = m;
    }

    // Gets items from the manager. if there are no items, return null
    private int[] safeGetItems() {
        try {
            return manager.getAndRemove();
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    @Override
    // Thread running method
    public void run() {
        super.run();
        int[] items;
        // while we can get a batch from the manager
        while ((items = safeGetItems()) != null) {
            int sum = 0;
            for (int item : items) {
                sum += item;
            }
            // add the resulting sum back to the manager
            manager.add(sum);
        }
    }
}
