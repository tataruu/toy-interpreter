package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class LogicExpTest {

    @Test
    void nonBooleanOperandThrowsException(){
        MyDictionary<String, IValue> symTable = new MyDictionary<>();
        IExp logicExp = new LogicExp(LogicOp.AND, new ValueExp(new IntValue(10)) , new ValueExp(new BoolValue(true)));
        assertThrows(ExpressionException.class, () -> logicExp.eval(symTable));
    }
}
