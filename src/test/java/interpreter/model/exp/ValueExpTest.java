package interpreter.model.exp;

import interpreter.exceptions.MyException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyHeap;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ValueExpTest {
    @Test
    void evalReturnsValueItself() throws MyException {
        ValueExp expInt = new ValueExp(new IntValue(10));
        ValueExp expBool = new ValueExp(new BoolValue(true));

        assertEquals(new IntValue(10), expInt.eval(new MyDictionary<>(), new MyHeap()));
        assertEquals(new BoolValue(true), expBool.eval(new MyDictionary<>(), new MyHeap()));
    }
}