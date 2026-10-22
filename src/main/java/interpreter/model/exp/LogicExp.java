package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;

public record LogicExp(LogicOp op, IExp left, IExp right) implements  IExp{

    @Override
    public IValue eval(IDictionary<String, IValue> symTable) throws MyException {
        if(!(left.eval(symTable) instanceof BoolValue(boolean val1)))
           throw new ExpressionException("first operand is not an bool");
        if(!(right.eval(symTable) instanceof BoolValue(boolean val2)))
            throw new ExpressionException("second operand is not an bool");

        if(op == LogicOp.AND)
            return new BoolValue(val1 && val2);
        else
            return new BoolValue(val1 || val2);





    }
}
