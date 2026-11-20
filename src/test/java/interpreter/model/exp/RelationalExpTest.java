package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IHeap;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyHeap;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RelationalExpTest {
    private final IDictionary<String, IValue> symTable = new MyDictionary<>();
    private final IHeap heap = new MyHeap();

    private boolean eval(int a, RelOp op, int b) throws MyException {
        IExp exp = new RelationalExp(op, new ValueExp(new IntValue(a)), new ValueExp(new IntValue(b)));
        return ((BoolValue) exp.eval(symTable, heap)).val();
    }

    @Test
    void lessThan() throws MyException {
        assertTrue(eval(1, RelOp.LT, 2));
        assertFalse(eval(2, RelOp.LT, 2));
    }

    @Test
    void lessOrEqual() throws MyException {
        assertTrue(eval(2, RelOp.LE, 2));
        assertFalse(eval(3, RelOp.LE, 2));
    }

    @Test
    void equal() throws MyException {
        assertTrue(eval(5, RelOp.EQ, 5));
        assertFalse(eval(5, RelOp.EQ, 6));
    }

    @Test
    void notEqual() throws MyException {
        assertTrue(eval(5, RelOp.NE, 6));
        assertFalse(eval(5, RelOp.NE, 5));
    }

    @Test
    void greaterThan() throws MyException {
        assertTrue(eval(3, RelOp.GT, 2));
        assertFalse(eval(2, RelOp.GT, 2));
    }

    @Test
    void greaterOrEqual() throws MyException {
        assertTrue(eval(2, RelOp.GE, 2));
        assertFalse(eval(1, RelOp.GE, 2));
    }

    @Test
    void nonIntegerFirstOperandThrows() {
        IExp exp = new RelationalExp(RelOp.LT, new ValueExp(new BoolValue(true)), new ValueExp(new IntValue(1)));
        assertThrows(ExpressionException.class, () -> exp.eval(symTable, heap));
    }

    @Test
    void nonIntegerSecondOperandThrows() {
        IExp exp = new RelationalExp(RelOp.LT, new ValueExp(new IntValue(1)), new ValueExp(new BoolValue(true)));
        assertThrows(ExpressionException.class, () -> exp.eval(symTable, heap));
    }
}
