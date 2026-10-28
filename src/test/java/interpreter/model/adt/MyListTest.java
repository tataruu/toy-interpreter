package interpreter.model.adt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MyListTest {
    private IList<Integer> list;

    @BeforeEach
    void setUp(){
        list = new MyList<>();
    }

    @Test
    void addAndGetTest(){
        list.add(10);
        list.add(20);
        assertEquals(10, list.get(0));
        assertEquals(20,list.get((1)));
    }

    @Test
    void sizeTest(){
        assertEquals(0,list.size());
        list.add(10);
        assertEquals(1, list.size());
    }
}
