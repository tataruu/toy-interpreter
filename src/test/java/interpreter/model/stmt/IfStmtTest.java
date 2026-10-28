package interpreter.model.stmt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.IStack;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.chrono.IsoChronology;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IfStmtTest {
    private PrgState state;

    @BeforeEach
    void setUp(){
        state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new NopStmt());

    }

    @Test
    void conditionTruePushesThenBranch() throws MyException {
        IStmt thenS = new NopStmt();
        IStmt elseS = new NopStmt();
        IStmt ifStmt = new IfStmt(new ValueExp(new BoolValue(true)), thenS , elseS);

        ifStmt.execute(state);

        assertEquals(thenS, state.getExeStack().pop());

    }

    @Test
    void nonBooleanConditionThrowsException(){
        IStmt ifStmt = new IfStmt(new ValueExp(new IntValue(10)), new NopStmt() , new NopStmt());
        assertThrows(StatementException.class, () -> ifStmt.execute(state));
    }
}
