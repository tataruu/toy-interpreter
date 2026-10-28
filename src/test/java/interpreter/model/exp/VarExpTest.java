package interpreter.model.exp;

import interpreter.exceptions.MyException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.IValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class VarExpTest {
    @Test
    void lookUpUndefinedVariableThrowsException(){
        MyDictionary<String, IValue> symTable = new MyDictionary<>();
        IExp varExp = new VarExp("x");
        assertThrows(MyException.class, () -> varExp.eval(symTable));
    }

}
