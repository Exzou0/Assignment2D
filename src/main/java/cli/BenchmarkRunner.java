package cli;

import algorithms.MaxHeap;
import metrics.PerformanceTracker;

import java.io.File;
import java.util.*;

public class BenchmarkRunner {
    public static void main(String[] args) throws Exception {
        int n = 10000;
        String dist = "random";
        String outPath = "docs/performance-plots/maxheap-results.csv";

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--n": n = Integer.parseInt(args[++i]); break;
                case "--dist": dist = args[++i]; break;
                case "--out": outPath = args[++i]; break;
            }
        }

        System.out.printf("Benchmark: n=%d, dist=%s, out=%s%n", n, dist, outPath);

        Integer[] data = generateInput(n, dist);
        PerformanceTracker tracker = new PerformanceTracker();
        MaxHeap<Integer> heap = new MaxHeap<>(n, tracker);

        long t0 = System.nanoTime();
        for (int x : data) heap.insert(x);
        long t1 = System.nanoTime();

        long t2 = System.nanoTime();
        while (!heap.isEmpty()) heap.extractMax();
        long t3 = System.nanoTime();

        long insertNs = t1 - t0;
        long extractNs = t3 - t2;

        System.out.printf("Insert time (ms): %.3f, Extract time (ms): %.3f%n",
                insertNs / 1e6, extractNs / 1e6);
        System.out.println(tracker);

        File out = new File(outPath);
        out.getParentFile().mkdirs();
        String header = "timestamp,n,insertNs,extractNs,comparisons,swaps,arrayAccesses";
        String line = tracker.toCsvLine(n, insertNs, extractNs);
        tracker.appendCsv(out, header, line);

        System.out.println("Result appended to " + out.getAbsolutePath());
    }

    private static Integer[] generateInput(int n, String dist) {
        Integer[] a = new Integer[n];
        switch (dist) {
            case "sorted":
                for (int i = 0; i < n; i++) a[i] = i;
                break;
            case "reversed":
                for (int i = 0; i < n; i++) a[i] = n - i;
                break;
            case "nearly-sorted":
                for (int i = 0; i < n; i++) a[i] = i;
                Random r = new Random(42);
                for (int j = 0; j < Math.max(1, n/100); j++) {
                    int p = r.nextInt(n), q = r.nextInt(n);
                    int tmp = a[p]; a[p] = a[q]; a[q] = tmp;
                }
                break;
            default:
                Random rnd = new Random(42);
                for (int i = 0; i < n; i++) a[i] = rnd.nextInt(n * 10);
        }
        return a;
    }
}
