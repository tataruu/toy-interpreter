package interpreter.controller;

import interpreter.model.type.IntType;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class GarbageCollectorTest {
    private final List<IValue> symTable = List.of(
            new RefValue(3 ,new IntType()), new RefValue(2, new IntType()));

    private final Map<Integer,IValue> heap = Map.of(
            1, new IntValue(20) , 2 , new RefValue(1, new IntType()),
            3, new IntValue(30), 4 , new IntValue(7)
    );

    @Test
    void unreachableLocationIsRemoved() {
        Set<Integer> roots = GarbageCollector.getAddrFromSymTable(symTable);
        Map<Integer, IValue> kept = GarbageCollector.safeGarbageCollector(roots, heap);
        assertFalse(kept.containsKey(4));
    }

    @Test
    void unsafeCollectorLosesTheLocationReachableThroughTheHeap() {
        Set<Integer> roots = GarbageCollector.getAddrFromSymTable(symTable);
        Map<Integer, IValue> kept = GarbageCollector.unsafeGarbageCollector(roots, heap);
        assertEquals(Set.of(2, 3), kept.keySet());
    }



    @Test
    void locationReachableOnlyThroughTheHeapIsKept() {
        Set<Integer> roots = GarbageCollector.getAddrFromSymTable(symTable);
        Map<Integer, IValue> kept = GarbageCollector.safeGarbageCollector(roots, heap);
        assertEquals(Set.of(1, 2, 3), kept.keySet());
    }


}
