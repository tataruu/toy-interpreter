package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MyStackTest {
    private IStack<Integer> stack;

    @BeforeEach
    void setUp(){
        stack = new MyStack<>();
    }

    @Test
    void respectLifoOrder() throws ADTException {
        stack.push(10);
        stack.push(20);
        assertEquals(20,stack.pop());
        assertEquals(10, stack.pop());

    }

    @Test
    void popOnEmptyStackThrowsException(){
        ADTException ex = assertThrows(ADTException.class, () -> stack.pop());
        assertEquals("Stack is empty", ex.getMessage());
    }

}
