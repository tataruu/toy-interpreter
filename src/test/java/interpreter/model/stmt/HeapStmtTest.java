package interpreter.model.stmt;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.exp.ReadHeapExp;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HeapStmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() throws MyException {
        state = PrgState.of(new NopStmt());
        new VarDeclStmt("v", new RefType(new IntType())).execute(state);   // Ref int v
        new VarDeclStmt("x", new IntType()).execute(state);                // int x
    }

    private static IExp num(int n) { return new ValueExp(new IntValue(n)); }

    @Test
    void newAllocatesAtAddressOneAndUpdatesTheVariable() throws MyException {
        new NewStmt("v", num(20)).execute(state);
        assertEquals(new RefValue(1, new IntType()), state.getSymTable().lookup("v"));
        assertEquals(new IntValue(20), state.getHeap().get(1));
    }

    private IValue eval(IExp exp) throws MyException {
        return exp.eval(state.getSymTable(), state.getHeap());
    }

    @Test
    void newOnAnUndeclaredVariableThrows() {
        assertThrows(StatementException.class, () -> new NewStmt("y", num(20)).execute(state));
    }

    @Test
    void newOnANonReferenceVariableThrows() {
        assertThrows(StatementException.class, () -> new NewStmt("x", num(20)).execute(state));
    }

    @Test
    void newWithAValueOfTheWrongTypeThrows() {
        var exp = new ValueExp(new BoolValue(true));
        assertThrows(StatementException.class, () -> new NewStmt("v", exp).execute(state));
    }


    @Test
    void readHeapReadsTheValue() throws MyException {
        new NewStmt("v", num(20)).execute(state);
        assertEquals(new IntValue(20), eval(new ReadHeapExp(new VarExp("v"))));
    }

    @Test
    void readHeapThroughTwoReferences() throws MyException {
        // Ex7: rH(rH(a))
        new NewStmt("v", num(20)).execute(state);
        new VarDeclStmt("a", new RefType(new RefType(new IntType()))).execute(state);
        new NewStmt("a", new VarExp("v")).execute(state);
        assertEquals(new IntValue(20), eval(new ReadHeapExp(new ReadHeapExp(new VarExp("a")))));
    }

    @Test
    void readHeapOfANonReferenceThrows() {
        assertThrows(ExpressionException.class, () -> eval(new ReadHeapExp(num(1))));
    }

    @Test
    void readHeapOfAnAddressNotInTheHeapThrows() {
        // v is still (0, int): address 0 is never in the heap
        assertThrows(ExpressionException.class, () -> eval(new ReadHeapExp(new VarExp("v"))));
    }

    // --- wH (S12)

    @Test
    void writeHeapChangesTheHeapLocation() throws MyException {
        new NewStmt("v", num(20)).execute(state);
        new WriteHeapStmt("v", num(30)).execute(state);
        assertEquals(new IntValue(30), state.getHeap().get(1));
        assertEquals(new RefValue(1, new IntType()), state.getSymTable().lookup("v"));  // v is unchanged
    }

    @Test
    void writeHeapOnAnUndeclaredVariableThrows() {
        assertThrows(StatementException.class, () -> new WriteHeapStmt("y", num(30)).execute(state));
    }

    @Test
    void writeHeapOnANonReferenceVariableThrows() {
        assertThrows(StatementException.class, () -> new WriteHeapStmt("x", num(30)).execute(state));
    }

    @Test
    void writeHeapToAnAddressNotInTheHeapThrows() {
        assertThrows(StatementException.class, () -> new WriteHeapStmt("v", num(30)).execute(state));
    }

    @Test
    void writeHeapWithAValueOfTheWrongTypeThrows() throws MyException {
        new NewStmt("v", num(20)).execute(state);
        var exp = new ValueExp(new BoolValue(true));
        assertThrows(StatementException.class, () -> new WriteHeapStmt("v", exp).execute(state));
    }


}
