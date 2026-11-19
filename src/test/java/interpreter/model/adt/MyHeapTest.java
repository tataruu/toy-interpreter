package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MyHeapTest {

    private IHeap heap;

    @BeforeEach
    void setUp(){
        heap = new MyHeap();
    }

    @Test
    void allocateReturnsConsecutiveAddressesStartingAtOne() {
        assertEquals(1, heap.allocate(new IntValue(10)));
        assertEquals(2, heap.allocate(new IntValue(20)));
        assertEquals(3, heap.allocate(new IntValue(30)));
    }

    @Test
    void getReturnsTheStoredValue() throws ADTException {
        int a = heap.allocate(new IntValue(10));
        assertEquals(new IntValue(10), heap.get(a));
    }

    @Test
    void getOfAMissingAddressThrows() {
        assertThrows(ADTException.class, () -> heap.get(1));
    }

    @Test
    void updateChangesTheValue() throws ADTException {
        int a = heap.allocate(new IntValue(10));
        heap.update(a, new IntValue(99));
        assertEquals(new IntValue(99), heap.get(a));
    }

    @Test
    void updateOfAMissingAddressThrows() {
        assertThrows(ADTException.class, () -> heap.update(1, new IntValue(5)));
    }



}
