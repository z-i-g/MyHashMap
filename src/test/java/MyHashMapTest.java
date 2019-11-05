import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

public class MyHashMapTest {

    MyHashMap myHashMap = new MyHashMap();

    @Test
    public void containsKeyReturnTrue() {
        myHashMap.put(8, 7);
        boolean conditionTrue = myHashMap.containsKey(8);
        assertTrue("the key is found", conditionTrue);
    }

    @Test
    public void containsKeyReturnFalse() {
        myHashMap.put(8, 7);
        boolean conditionFalse = myHashMap.containsKey(7);
        assertFalse("the key is not found", conditionFalse);
    }

    @Test
    public void removeAbsentKeyReturnFalse() {
        myHashMap.put(2, 4);
        assertFalse(myHashMap.remove(3));
    }

    @Test
    public void removeExistingKeyReturnTrue(){
        myHashMap.put(2, 4);
        assertTrue(myHashMap.remove(2));
    }

    @Test
    public void putHundredIntegersAllStored() {
        for(int i = 0; i < 100; i++) {
            myHashMap.put(i, i + 1);
            assertTrue(myHashMap.get(i) == (Object)(i+1));
        }
    }

    @Test
    public void putRewrite() {
        myHashMap.put(1, 5);
        myHashMap.put(1, 7);
        assertTrue(myHashMap.get(1) == (Object)7);
        assertFalse(myHashMap.get(1) == (Object)5);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Test
    public void putIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(not(equalTo("")));
        myHashMap.put(null, 7);
        thrown = ExpectedException.none();
    }

    @Test
    public void getExistingKeyReturnValue() {
        myHashMap.put(1, 2);
        assertEquals(myHashMap.get(1), 2);
    }

    @Test
    public void getAbsentKeyReturnNull() {
        assertNull(myHashMap.get(2));
    }

    @Test
    public void sizeHundredTwoNundredElements() {
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

    @Test
    public void sizeThenRemoved() {
        for(int i = 0; i < 100; i++) {
            myHashMap.put(i, i);
        }

        for(int i = 0; i < 100; i++) {
            myHashMap.remove(i);
        }
        assertEquals(0, myHashMap.size());
    }
}