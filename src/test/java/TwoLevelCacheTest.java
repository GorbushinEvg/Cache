//import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TwoLevelCacheTest {
    private List<Object> listOfTestObjects = new ArrayList<Object>();
    private static final short MAX_SIZE_OF_STORAGE = 10;

    private TwoLevelCache<String, Object> twoLevelCache;

    @Before
    public void setUp() throws Exception {
        //ICacheStrategy<String, Object> strategy = new LRUStrategy<String, Object>();
        ICacheStrategy<String, Object> strategy = new LFUStrategy<String, Object>();

        L1Cache<String, Object> l1Cache = new L1Cache<String, Object>(strategy, 10);
        L2Cache<String, Object> l2Cache = new L2Cache<String, Object>(strategy, 10);

        twoLevelCache = new TwoLevelCache<String, Object>(l1Cache, l2Cache);

        for (int i = 0; i < MAX_SIZE_OF_STORAGE + 10; i++) {
            listOfTestObjects.add(new TestObject(i));
        }
    }

    @Test
    public void put() {
        for (int i = 0; i < 2; i++) {
            twoLevelCache.put(String.valueOf(i), listOfTestObjects.get(i));
        }
        assertEquals(twoLevelCache.size(), 2);
    }

    @Test
    public void putAndReturnRemovedObject() {
        for (int i = 0; i < MAX_SIZE_OF_STORAGE; i++) {
            twoLevelCache.put(String.valueOf(i), listOfTestObjects.get(i));
        }
        Object newObject = listOfTestObjects.get(MAX_SIZE_OF_STORAGE);
        Pair<String, Object> removedObject = twoLevelCache.putAndReturnRemovedObject(String.valueOf(MAX_SIZE_OF_STORAGE), newObject);
        assertNotNull(removedObject);
    }

    @Test
    public void get() {
        for (int i = 0; i < 2; i++) {
            twoLevelCache.put(String.valueOf(i), listOfTestObjects.get(i));
        }
        Object exceptedObject =  twoLevelCache.get("1");
        assertEquals(exceptedObject, listOfTestObjects.get(1));
    }

    @Test
    public void remove() {
        for (int i = 0; i < 3; i++) {
            twoLevelCache.put(String.valueOf(i), listOfTestObjects.get(i));
        }
        assertEquals(twoLevelCache.size(), 3);

        twoLevelCache.remove("4");
        assertEquals(twoLevelCache.size(), 3);

        twoLevelCache.remove("1");
        assertEquals(twoLevelCache.size(), 2);
    }

    @Test
    public void size() {
        for (int i = 0; i < 5; i++) {
            twoLevelCache.put(String.valueOf(i), listOfTestObjects.get(i));
        }
        assertEquals(twoLevelCache.size(), 5);
    }

    @Test
    public void clear() {
        for (int i = 0; i < 5; i++) {
            twoLevelCache.put(String.valueOf(i), listOfTestObjects.get(i));
        }
        twoLevelCache.clear();
        assertEquals(twoLevelCache.size(), 0);
    }
}