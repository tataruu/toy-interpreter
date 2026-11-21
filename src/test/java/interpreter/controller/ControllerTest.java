package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.NopStmt;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.swing.plaf.nimbus.State;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

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


    @Test
    void example6PrintsTheTwoReferences() throws MyException {
        PrgState s = run(Examples.EX6);
        assertEquals(List.of(new RefValue(1, new IntType()), new RefValue(2, new RefType(new IntType()))),
                s.getOut().toList());
        assertEquals(Map.of(1, new IntValue(20), 2, new RefValue(1, new IntType())), s.getHeap().getContent());
    }

    @Test
    void example7Prints20And25() throws MyException {
        assertEquals(List.of(new IntValue(20), new IntValue(25)), run(Examples.EX7).getOut().toList());
    }

    @Test
    void example8Prints20And35() throws MyException {
        PrgState s = run(Examples.EX8);
        assertEquals(List.of(new IntValue(20), new IntValue(35)), s.getOut().toList());
        assertEquals(Map.of(1, new IntValue(30)), s.getHeap().getContent());
    }

    @Test
    void example9Prints20WithTheSafeCollector() throws MyException {
        assertEquals(List.of(new IntValue(20)), run(Examples.EX9).getOut().toList());
    }

    @Test
    void example10CountsDownFrom4() throws MyException {
        List<IValue> expected = List.of(new IntValue(4), new IntValue(3), new IntValue(2),
                new IntValue(1), new IntValue(0));
        assertEquals(expected, run(Examples.EX10).getOut().toList());
    }

}
