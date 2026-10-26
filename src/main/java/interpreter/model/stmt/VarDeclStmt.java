package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.IDictionary;
import interpreter.model.exp.IExp;
import interpreter.model.type.IType;
import interpreter.model.value.IValue;

public record VarDeclStmt(String name, IType type) implements IStmt{


    @Override
    public PrgState execute(PrgState state) throws MyException{
        IDictionary<String, IValue> symTable  = state.getSymTable();
        if(symTable.isDefined(name))
            throw new MyException("Variabila " + name + " a fost deja declarata");

        else
            symTable.put(name,type.defaultValue());

        return null;
    }

    @Override
    public String toString(){
        return type.toString() + " " + name;
    }
    }


