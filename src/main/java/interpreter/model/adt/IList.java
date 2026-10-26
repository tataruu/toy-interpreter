package interpreter.model.adt;

import interpreter.model.value.IValue;

public interface IList<T> {

    void add(T elem);
    T get(int index);
    int size();

}
