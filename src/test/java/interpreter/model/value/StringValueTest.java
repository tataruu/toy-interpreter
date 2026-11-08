package interpreter.model.value;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.NopStmt;
import interpreter.model.stmt.VarDeclStmt;
import interpreter.model.type.StringType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class StringValueTest {

    @Test
    void stringVariaibleGetsEmptyStringAsDefault() throws MyException {
        PrgState state = PrgState.of(new NopStmt());
        new VarDeclStmt("s", new StringType()).execute((state));
        assertEquals(new StringValue(""), state.getSymTable().lookup("s"));

    }

    @Test
    void equalStringValuesAreEqualAndHaveSameHashCode() {
        StringValue s1 = new StringValue("a");
        StringValue s2 = new StringValue("a");
        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());
        assertNotEquals(s1, new StringValue("b"));
    }
}


