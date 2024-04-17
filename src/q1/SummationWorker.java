package q1;

public class SummationWorker extends Thread {
    private final ParallelSummingManager manager;
    public SummationWorker(ParallelSummingManager m) {
        super();
        manager = m;
    }

    private int[] safeGetItems() {
        int[] items;
        try {
            items = manager.getAndRemove();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Done");
            return null;
        }
        return items;
    }

    @Override
    public void run() {
        super.run();
        int[] items;
        while ((items = safeGetItems()) != null) {
            int sum = 0;
            for (int item : items) {
                sum += item;
            }
            manager.add(sum);
        }

    }
}
