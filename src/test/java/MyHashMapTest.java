import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

public class MyHashMapTest {

    MyHashMap myHashMap = new MyHashMap();

    @Test
    public void containsKey() {
        myHashMap.put(8, 7);
        boolean conditionTrue = myHashMap.containsKey(8);
        boolean conditionFalse = myHashMap.containsKey(7);
        assertTrue("the key is found", conditionTrue);
        assertFalse("the key is not found", conditionFalse);
    }

    @Test
    public void remove() {
        myHashMap.put(2, 4);
        assertTrue(myHashMap.remove(2));
        assertFalse(myHashMap.remove(3));
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void put() {
        for(int i = 0; i < 100; i++) {
            myHashMap.put(i, i + 1);
            assertTrue(myHashMap.get(i) == (Object)(i+1));
        }

        myHashMap.put(1, 5);
        myHashMap.put(1, 7);
        assertTrue(myHashMap.get(1) == (Object)7);
        assertFalse(myHashMap.get(1) == (Object)5);

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(not(equalTo("")));
        myHashMap.put(null, 7);
        thrown = ExpectedException.none();
    }

    @Test
    public void get() {
        myHashMap.put(1, 2);
        assertEquals(myHashMap.get(1), 2);
        assertNull(myHashMap.get(2));

    }

    @Test
    public void size() {
        for(int i = 0; i < 100; i++) {
            myHashMap.put(i, i);
            myHashMap.put(i, i);
        }
        assertEquals(100, myHashMap.size());

        for(int i = 100; i < 200; i++) {
            myHashMap.put(i, i);
            myHashMap.put(i, i);
        }
        assertEquals(200, myHashMap.size());
    }
}