package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ArithExp;
import interpreter.model.exp.ArithOp;
import interpreter.model.exp.RelOp;
import interpreter.model.exp.RelationalExp;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WhileStmtTest {
    private PrgState state;

    // while (v > 0) (print(v); v = v - 1)
    private final IStmt loop = new WhileStmt(
            new RelationalExp(RelOp.GT, new VarExp("v"), new ValueExp(new IntValue(0))),
            new CompStmt(new PrintStmt(new VarExp("v")),
                    new AssignStmt("v", new ArithExp(ArithOp.SUB, new VarExp("v"), new ValueExp(new IntValue(1))))));

    @BeforeEach
    void setUp() {
        state = PrgState.of(new NopStmt());
    }

    // runs the loop with v = start until the stack is empty
    private void runLoop(int start) throws MyException {
        state.getSymTable().put("v", new IntValue(start));
        state.getExeStack().push(loop);
        while (!state.getExeStack().isEmpty())
            state.getExeStack().pop().execute(state);
    }

    @Test
    void loopRunsZeroTimes() throws MyException {
        runLoop(0);
        assertEquals(List.of(), state.getOut().toList());
    }

    @Test
    void loopRunsOnce() throws MyException {
        runLoop(1);
        assertEquals(List.of(new IntValue(1)), state.getOut().toList());
    }

    @Test
    void loopRunsThreeTimes() throws MyException {
        runLoop(3);
        assertEquals(List.of(new IntValue(3), new IntValue(2), new IntValue(1)), state.getOut().toList());
    }

    @Test
    void nonBooleanConditionThrows() {
        IStmt bad = new WhileStmt(new ValueExp(new IntValue(1)), new NopStmt());
        assertThrows(StatementException.class, () -> bad.execute(state));
    }
}