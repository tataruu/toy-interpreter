package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyDictionaryTest {
    private IDictionary<String, IValue> dict;

    @BeforeEach
    void setUp(){
        dict = new MyDictionary<>();
    }

    @Test
    void putAndLookUpTest() throws MyException {
        dict.put("v" , new IntValue(10));
        assertTrue(dict.isDefined("v"));
        assertEquals(new IntValue(10), dict.lookup("v"));
    }

    @Test
    void updateChangesValue() throws ADTException {
        dict.put("v" , new IntValue(10));
        dict.update("v" , new IntValue(20));
        assertEquals(new IntValue(20) , dict.lookup("v"));

    }

    @Test
    void lookUpMissingKeyThrowsException(){
        assertThrows(ADTException.class, () -> dict.lookup("missing"));
    }
}
