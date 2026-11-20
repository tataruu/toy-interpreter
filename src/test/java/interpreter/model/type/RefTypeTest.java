package interpreter.model.type;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.stmt.NopStmt;
import interpreter.model.stmt.VarDeclStmt;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class RefTypeTest {

     @Test
     void refVariableGetsAddressZeroAsDefault() throws MyException {
         PrgState state = PrgState.of(new NopStmt());
         new VarDeclStmt("v", new RefType(new IntType())).execute(state);   // Ref int v
         assertEquals(new RefValue(0, new IntType()), state.getSymTable().lookup("v"));
     }

    @Test
    void nestedRefTypesAreComparedRecursively() {
        assertEquals(new RefType(new RefType(new IntType())), new RefType(new RefType(new IntType())));
        assertNotEquals(new RefType(new RefType(new IntType())), new RefType(new IntType()));
    }

 }
