package algorithms;

import metrics.PerformanceTracker;
import java.util.NoSuchElementException;

public class MaxHeap<T extends Comparable<? super T>> {
    private Object[] heap;
    private int size;
    private final PerformanceTracker tracker;

    public MaxHeap(int capacity, PerformanceTracker tracker) {
        this.heap = new Object[Math.max(1, capacity)];
        this.tracker = tracker == null ? new PerformanceTracker() : tracker;
    }
    public MaxHeap() { this(16, new PerformanceTracker()); }

    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }

    private void ensureCapacity() {
        if (size == heap.length) {
            Object[] newHeap = new Object[heap.length * 2];
            System.arraycopy(heap, 0, newHeap, 0, heap.length);
            tracker.addArrayAccess(heap.length * 2);
            heap = newHeap;
        }
    }

    public void insert(T value) {
        if (value == null) throw new IllegalArgumentException("null not allowed");
        ensureCapacity();
        heap[size] = value;
        tracker.addArrayAccess(1);
        siftUp(size++);
    }

    public T peekMax() {
        if (isEmpty()) throw new NoSuchElementException();
        tracker.addArrayAccess(1);
        return (T) heap[0];
    }

    public T extractMax() {
        if (isEmpty()) throw new NoSuchElementException();
        T max = (T) heap[0];
        heap[0] = heap[--size];
        heap[size] = null;
        tracker.addArrayAccess(3);
        if (!isEmpty()) siftDown(0);
        return max;
    }

    public void increaseKey(int index, T newValue) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        tracker.addArrayAccess(1);
        T cur = (T) heap[index];
        tracker.incrementComparisons(1);
        if (newValue.compareTo(cur) < 0) throw new IllegalArgumentException("new key smaller");
        heap[index] = newValue;
        tracker.addArrayAccess(1);
        siftUp(index);
    }

    private void siftUp(int i) {
        while (i > 0) {
            int p = (i - 1) / 2;
            tracker.incrementComparisons(1);
            tracker.addArrayAccess(2);
            if (((T)heap[i]).compareTo((T)heap[p]) > 0) {
                swap(i, p);
                i = p;
            } else break;
        }
    }

    private void siftDown(int i) {
        while (true) {
            int l = 2 * i + 1, r = l + 1, largest = i;
            if (l < size && greater(l, largest)) largest = l;
            if (r < size && greater(r, largest)) largest = r;
            if (largest != i) { swap(i, largest); i = largest; }
            else break;
        }
    }

    private boolean greater(int i, int j) {
        tracker.incrementComparisons(1);
        tracker.addArrayAccess(2);
        return ((T)heap[i]).compareTo((T)heap[j]) > 0;
    }

    private void swap(int i, int j) {
        Object t = heap[i]; heap[i] = heap[j]; heap[j] = t;
        tracker.incrementSwaps(1); tracker.addArrayAccess(4);
    }
}
