package algorithms;

import metrics.PerformanceTracker;
import java.util.NoSuchElementException;

public class MaxHeap {
    private int[] heap;
    private int size;
    private final PerformanceTracker tracker;

    public MaxHeap(int capacity, PerformanceTracker tracker) {
        heap = new int[Math.max(1, capacity)];
        this.tracker = tracker == null ? new PerformanceTracker() : tracker;
    }

    public MaxHeap() { this(16, new PerformanceTracker()); }

    public void insert(int v) {
        ensureCapacity();
        heap[size] = v;
        tracker.addArrayAccess(1);
        siftUp(size++);
    }

    public int peekMax() {
        if (size == 0) throw new NoSuchElementException();
        tracker.addArrayAccess(1);
        return heap[0];
    }

    public int extractMax() {
        if (size == 0) throw new NoSuchElementException();
        int m = heap[0];
        heap[0] = heap[--size];
        heap[size] = 0;
        tracker.addArrayAccess(3);
        if (size > 0) siftDown(0);
        return m;
    }

    public void increaseKey(int i, int nv) {
        if (i < 0 || i >= size) throw new IndexOutOfBoundsException();
        if (nv < heap[i]) throw new IllegalArgumentException();
        heap[i] = nv;
        tracker.addArrayAccess(1);
        siftUp(i);
    }

    private void siftUp(int i) {
        while (i > 0) {
            int p = (i - 1) / 2;
            tracker.incrementComparisons(1);
            tracker.addArrayAccess(2);
            if (heap[i] > heap[p]) { swap(i, p); i = p; } else break;
        }
    }

    private void siftDown(int i) {
        while (true) {
            int l = 2 * i + 1, r = 2 * i + 2, g = i;
            if (l < size && heap[l] > heap[g]) g = l;
            if (r < size && heap[r] > heap[g]) g = r;
            if (g != i) { swap(i, g); i = g; } else break;
        }
    }

    private void swap(int i, int j) {
        int t = heap[i]; heap[i] = heap[j]; heap[j] = t;
        tracker.incrementSwaps(1); tracker.addArrayAccess(4);
    }

    private void ensureCapacity() {
        if (size == heap.length) {
            int[] n = new int[heap.length * 2];
            System.arraycopy(heap, 0, n, 0, heap.length);
            tracker.addArrayAccess(heap.length * 2);
            heap = n;
        }
    }
}
