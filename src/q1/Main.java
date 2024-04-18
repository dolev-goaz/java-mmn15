package q1;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final int MAX_VALUE = 100;
    public static void main(String[] args) {
        final int datasetSize = getPositiveNumber("How many elements?");
        final int threadCount = getPositiveNumber("How many processes?");
        Integer[] dataSet = getDataset(datasetSize);
        ParallelSummingManager parallelSumming = new ParallelSummingManager(dataSet, threadCount);
        System.out.println(parallelSumming.parallelSum());
    }

    private static int getPositiveNumber(String message) {
        Scanner s = new Scanner(System.in);
        int out;
        while (true) {
            System.out.print(message + " ");
            try {
                out = s.nextInt();
                if (out > 0) {
                    return out;
                }
                System.out.println("Please enter a positive number!");
            } catch (InputMismatchException e) {
                s.nextLine(); // clear buffer
                System.out.println("Please enter a numeric value!");
            }
        }
    }

    private static Integer[] getDataset(int datasetSize) {
        Integer[] out = new Integer[datasetSize];
        Random rnd = new Random();
        for (int i = 0; i < datasetSize; i++) {
            out[i] = 1 + rnd.nextInt(MAX_VALUE);
        }
        return out;
    }
}
