package interpreter.model.exp;

import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IHeap;
import interpreter.model.type.RefType;
import interpreter.model.value.IValue;
import interpreter.model.value.RefValue;
import interpreter.repository.Repository;

public record ReadHeapExp(IExp exp) implements IExp {
    @Override
    public IValue eval (IDictionary<String, IValue> symTable, IHeap heap) throws MyException {
        if(!(exp.eval(symTable, heap) instanceof RefValue ref))
            throw new MyException("Invalid expression");
        if(!(heap.isDefined(ref.address())))
            throw new  MyException("Invalid expression");
        return heap.get(ref.address());
    }

    @Override
    public String toString(){
        return "rH(" + exp + ")";
    }
}
