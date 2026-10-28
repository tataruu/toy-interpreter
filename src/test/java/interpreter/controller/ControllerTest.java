package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.NopStmt;
import interpreter.model.value.IntValue;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import org.junit.jupiter.api.Test;

import javax.swing.plaf.nimbus.State;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    private static PrgState run(IStmt program) throws MyException{
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), program);
        new Controller(new Repository(state),false).allStep();
        return state;

    }
    @Test
    void example2PrintsEighteen() throws MyException{
        PrgState state = run(Examples.EX2);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(new IntValue(18), state.getOut().get(0));
    }

    @Test
    void oneStepOnEmptyStackThrowsException() {
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new NopStmt());
        Controller ctrl = new Controller(new Repository(state), false);
        try{
            ctrl.oneStep(state); // runs nopstmt
            assertThrows(StatementException.class, () -> ctrl.oneStep(state));

        }
        catch (MyException e) {
            fail("Unexpected error");
        }

    }
}
