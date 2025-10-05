package metrics;

import java.io.*;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

public class PerformanceTracker {
    private final AtomicLong comparisons = new AtomicLong(0);
    private final AtomicLong swaps = new AtomicLong(0);
    private final AtomicLong arrayAccesses = new AtomicLong(0);

    public void incrementComparisons(long n) { comparisons.addAndGet(n); }
    public void incrementSwaps(long n) { swaps.addAndGet(n); }
    public void addArrayAccess(long n) { arrayAccesses.addAndGet(n); }

    public long getComparisons() { return comparisons.get(); }
    public long getSwaps() { return swaps.get(); }
    public long getArrayAccesses() { return arrayAccesses.get(); }

    public void reset() {
        comparisons.set(0);
        swaps.set(0);
        arrayAccesses.set(0);
    }

    public String toCsvLine(long n, long insertNs, long extractNs) {
        return String.format("%s,%d,%d,%d,%d,%d,%d",
                DateTimeFormatter.ISO_INSTANT.format(Instant.now()),
                n, insertNs, extractNs,
                getComparisons(), getSwaps(), getArrayAccesses());
    }

    public void appendCsv(File out, String header, String line) throws IOException {
        boolean exists = out.exists();
        try (FileWriter fw = new FileWriter(out, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            if (!exists) {
                bw.write(header);
                bw.newLine();
            }
            bw.write(line);
            bw.newLine();
        }
    }

    @Override
    public String toString() {
        return "PerformanceTracker{" +
                "comparisons=" + getComparisons() +
                ", swaps=" + getSwaps() +
                ", arrayAccesses=" + getArrayAccesses() +
                '}';
    }
}
