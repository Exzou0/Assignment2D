package cli;

import algorithms.MaxHeap;
import metrics.PerformanceTracker;

import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Random;

public class BenchmarkRunner {
    public static void main(String[] args) throws IOException {
        int[] sizes = {1000, 5000, 10000, 20000, 50000, 100000};
        String csvPath = "docs/performance-plots/maxheap-results.csv";

        try (FileWriter writer = new FileWriter(csvPath)) {
            writer.write("n,insertNs,extractNs,comparisons,swaps\n");

            for (int n : sizes) {
                PerformanceTracker tracker = new PerformanceTracker();
                MaxHeap heap = new MaxHeap(n, tracker);
                Random random = new Random(42);

                long startInsert = System.nanoTime();
                for (int i = 0; i < n; i++) {
                    heap.insert(random.nextInt());
                }
                long insertTime = System.nanoTime() - startInsert;

                long startExtract = System.nanoTime();
                try {
                    while (true) {
                        heap.extractMax();
                    }
                } catch (NoSuchElementException e) {
                }
                long extractTime = System.nanoTime() - startExtract;

                writer.write(String.format("%d,%d,%d,%d,%d\n",
                        n, insertTime, extractTime,
                        tracker.getComparisons(), tracker.getSwaps()));
                writer.flush();

                System.out.printf("Completed benchmark for n=%d%n", n);
            }
        }

        System.out.println("Benchmark complete. Results saved to " + csvPath);
    }
}
