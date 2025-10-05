package algorithms;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;

public class MaxHeapTest {

    @Test
    public void testInsertExtract() {
        Integer[] input = {5, 1, 9, 3, 7};
        MaxHeap<Integer> heap = new MaxHeap<>();
        for (int v : input) heap.insert(v);

        Arrays.sort(input, Collections.reverseOrder());
        for (int v : input) {
            assertEquals(v, heap.extractMax());
        }
    }

    @Test
    public void testIncreaseKey() {
        MaxHeap<Integer> heap = new MaxHeap<>();
        heap.insert(1);
        heap.insert(2);
        heap.insert(3);

        heap.increaseKey(2, 10);
        assertEquals(10, heap.extractMax());
    }

    @Test
    public void testEdgeCases() {
        MaxHeap<Integer> heap = new MaxHeap<>();
        assertThrows(Exception.class, heap::peekMax);
        assertThrows(Exception.class, heap::extractMax);
    }
}
