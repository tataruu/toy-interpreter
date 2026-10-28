package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompStmtTest {

    @Test
    void executePushesStatementInReverseOrder() throws MyException{
        IStmt s1 = new NopStmt();
        IStmt s2 = new NopStmt();
        IStmt comp = new CompStmt(s1,s2);

        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new NopStmt());
        state.getExeStack().pop();
        comp.execute(state);

        assertEquals(s1, state.getExeStack().pop());
        assertEquals(s2, state.getExeStack().pop());

    }
}
