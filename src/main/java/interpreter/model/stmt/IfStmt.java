package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.IStack;
import interpreter.model.exp.IExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;

public record IfStmt(IExp cond, IStmt thenS, IStmt elseS)  implements IStmt{

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue condValue = cond.eval(state.getSymTable());
        if (condValue instanceof BoolValue boolVal) {
            IStack<IStmt> stack = state.getExeStack();
            if (boolVal.val())
                stack.push(thenS);
            else
                stack.push(elseS);

            return state;
        }
        else
            throw new StatementException("Condition in if is not bool");



    }
}
