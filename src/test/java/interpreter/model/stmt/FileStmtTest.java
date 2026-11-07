package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileStmtTest {

    @TempDir
    Path dir;

    private PrgState state;
    private ValueExp file;
    private StringValue fileName;

    @BeforeEach
    void setUp() throws Exception {
        Path f = dir.resolve("numbers.in");
        Files.writeString(f, "7\n");
        fileName = new StringValue(f.toString());
        file = new ValueExp(fileName);
        state = PrgState.of(new NopStmt());
        new VarDeclStmt("x", new IntType()).execute(state);


    }
    // --- openRFile (S8)

    @Test
    void openAddsTheFileToTheFileTable() throws MyException {
        new OpenRFileStmt(file).execute(state);
        assertTrue(state.getFileTable().isDefined(fileName));
    }

    @Test
    void openingANonStringThrows() {
        var stmt = new OpenRFileStmt(new ValueExp(new IntValue(1)));
        assertThrows(StatementException.class, () -> stmt.execute(state));
    }

    @Test
    void openingTheSameFileTwiceThrows() throws MyException {
        new OpenRFileStmt(file).execute(state);
        assertThrows(StatementException.class, () -> new OpenRFileStmt(file).execute(state));
    }

    // --- readFile

    @Test
    void readingNonIntLineThrows() throws Exception {
        Path f = dir.resolve("bad.in");
        Files.writeString(f, "abc\n");
        var bad = new ValueExp(new StringValue(f.toString()));
        new OpenRFileStmt(bad).execute(state);
        assertThrows(StatementException.class, () -> new ReadFileStmt(bad, "x").execute(state));


    }
    @Test
    void readFromAFileThatIsNotOpenThrows() {
        assertThrows(StatementException.class, () -> new ReadFileStmt(file, "x").execute(state));
    }

    @Test
    void readFromNonStringThrows() {
        var stmt = new ReadFileStmt(new ValueExp(new IntValue(1)), "x");
        assertThrows(StatementException.class, () -> stmt.execute(state));
    }

    @Test
    void readIntoNonIntVariableThrows() throws MyException {
        new VarDeclStmt("b", new BoolType()).execute(state);
        new OpenRFileStmt(file).execute(state);
        assertThrows(StatementException.class, () -> new ReadFileStmt(file, "b").execute(state));
    }

    @Test
    void readGivesTheNextNumberThenZeroAtTheEnd() throws MyException {
        new OpenRFileStmt(file).execute(state);
        new ReadFileStmt(file, "x").execute(state);
        assertEquals(new IntValue(7), state.getSymTable().lookup("x"));
        new ReadFileStmt(file, "x").execute(state);
        assertEquals(new IntValue(0), state.getSymTable().lookup("x"));
    }

    @Test
    void readReadsTheNumbersInOrder() throws Exception {
        Path f = dir.resolve("more.in");
        Files.writeString(f, "15\n  50 \n");
        var more = new ValueExp(new StringValue(f.toString()));
        new OpenRFileStmt(more).execute(state);
        new ReadFileStmt(more, "x").execute(state);
        assertEquals(new IntValue(15), state.getSymTable().lookup("x"));
        new ReadFileStmt(more, "x").execute(state);
        assertEquals(new IntValue(50), state.getSymTable().lookup("x"));   // spaces are stripped
    }

    @Test
    void readIntoUndeclaredVariableThrows() throws MyException {
        new OpenRFileStmt(file).execute(state);
        assertThrows(StatementException.class, () -> new ReadFileStmt(file, "y").execute(state));
    }


    // --- closeRFile (S10)

    @Test
    void closeRemovesTheFileFromTheFileTable() throws MyException {
        new OpenRFileStmt(file).execute(state);
        new CloseRFileStmt(file).execute(state);
        assertFalse(state.getFileTable().isDefined(fileName));
    }

    @Test
    void closingAFileThatIsNotOpenThrows() {
        assertThrows(StatementException.class, () -> new CloseRFileStmt(file).execute(state));
    }

    @Test
    void closingANonStringThrows() {
        var stmt = new CloseRFileStmt(new ValueExp(new IntValue(1)));
        assertThrows(StatementException.class, () -> stmt.execute(state));
    }



}



