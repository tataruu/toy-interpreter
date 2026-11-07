package interpreter.model.adt;

import interpreter.model.value.IValue;

import java.util.List;

public interface IList<T> {

    void add(T elem);
    T get(int index);
    int size();
    List<T> toList();

}
