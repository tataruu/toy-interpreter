package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AssignStmtTest {
    private PrgState state;

    @BeforeEach
    void setUp(){
        state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new NopStmt());

    }

    @Test
    void executedUpdatesDeclaratedVariable() throws MyException{
        state.getSymTable().put("v" , new IntType().defaultValue());
        IStmt assign = new AssignStmt("v", new ValueExp(new IntValue(5)));
        assign.execute(state);
        assertEquals(new IntValue(5) , state.getSymTable().lookup("v"));
    }

    @Test
    void executeUndeclaredVariableThrowsExceptiom(){
        IStmt assign = new AssignStmt("v", new ValueExp(new IntValue(5)));
        assertThrows(StatementException.class, () -> assign.execute(state));
    }

    @Test
    void executeTypeMismatchThrowsException() throws  MyException{
        state.getSymTable().put("v", new IntType().defaultValue());
        IStmt assign = new AssignStmt("v", new ValueExp(new BoolValue(true)));
        assertThrows(StatementException.class, () -> assign.execute(state));

    }
}
