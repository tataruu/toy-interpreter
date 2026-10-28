package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import java.awt.print.PrinterGraphics;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrintStmtTest {

    @Test
    void executeAddsValueToOutList() throws MyException{
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new NopStmt());
        IStmt printStmt = new PrintStmt(new ValueExp(new IntValue(10)));

        printStmt.execute(state);
        assertEquals(new IntValue(10), state.getOut().get(0));
    }
}
