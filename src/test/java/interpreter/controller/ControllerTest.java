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
import org.junit.jupiter.api.io.TempDir;

import javax.swing.plaf.nimbus.State;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    @TempDir
    Path dir;

    private  PrgState run(IStmt program) throws MyException{
        PrgState state = PrgState.of(program);
        var repo = new Repository(state, dir.resolve("log.txt").toString());
        new Controller(repo, false).allStep();
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
        PrgState state = PrgState.of(new NopStmt());
        Controller ctrl = new Controller(new Repository(state ,dir.resolve("log.txt").toString()), false);
        try{
            ctrl.oneStep(state); // runs nopstmt
            assertThrows(StatementException.class, () -> ctrl.oneStep(state));

        }
        catch (MyException e) {
            fail("Unexpected error");
        }

    }

    @Test
    void fileExampleReadsBothNumbersAndClosesFile() throws Exception{
        Path in = dir.resolve("test.in");
        Files.writeString(in,"15\n16\n");
        PrgState s = run(Examples.fileExample(in.toString()));
        assertEquals(new IntValue(15), s.getOut().get(0));
        assertEquals(new IntValue(16), s.getOut().get(1));
        assertTrue(s.getFileTable().toMap().isEmpty());
    }

    @Test
    void fileExampleEmptyFilePrintsZeroTwice() throws Exception{
        Path in = dir.resolve("empty.in");
        Files.writeString(in,"");
        PrgState s = run(Examples.fileExample(in.toString()));
        assertEquals(new IntValue(0), s.getOut().get(0));
        assertEquals(new IntValue(0), s.getOut().get(1));
        assertTrue(s.getFileTable().toMap().isEmpty());

    }

    @Test
    void example5PrintsFive() throws MyException{
        PrgState state = run(Examples.EX5);
        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(5), state.getOut().get(0));

    }


}
