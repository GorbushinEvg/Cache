//import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;
import static org.junit.Assert.*;

public class CacheTest {
    private List<Object> listOfTestObjects = new ArrayList<Object>();
    private static final short MAX_SIZE_OF_STORAGE = 10;

    private Cache<String, Object> cache;

    @Before
    public void setUp() throws Exception {
        //ICacheStorage<String, Object> storage = new MemoryStorage<String, Object>(MAX_SIZE_OF_STORAGE);
        ICacheStorage<String, Object> storage = new FileSystemStorage<String, Object>(MAX_SIZE_OF_STORAGE);
        //ICacheStrategy<String, Object> strategy = new LRUStrategy<String, Object>();
        ICacheStrategy<String, Object> strategy = new LFUStrategy<String, Object>();
        cache = new Cache<String, Object>(storage, strategy);

        for (int i = 0; i < MAX_SIZE_OF_STORAGE + 10; i++) {
            listOfTestObjects.add(new TestObject(i));
        }
    }

    @Test
    public void put() {
        Object object = listOfTestObjects.get(1);
        String key = ((TestObject) object).getStringField();
        cache.put(key, object);

        assertEquals(cache.size(), 1);
    }

    @Test
    public void put_MANY_EQUALS_OBJECTS() {
        for (int i = 0; i < listOfTestObjects.size(); i++) {
            Object object = listOfTestObjects.get(0);
            String key = ((TestObject) object).getStringField();
            cache.put(key, object);
        }

        assertEquals(cache.size(), 1);
    }

    @Test
    public void putAndReturnRemovedObject() {
        for (int i = 0; i < MAX_SIZE_OF_STORAGE; i++) {
            Object object = listOfTestObjects.get(i);
            String key = ((TestObject) object).getStringField();
            cache.put(key, object);
        }
        Object newObject = listOfTestObjects.get(MAX_SIZE_OF_STORAGE);
        Pair<String, Object> removedObject = cache.putAndReturnRemovedObject(((TestObject) newObject).getStringField(), newObject);

        assertNotNull(removedObject);
    }

    @Test
    public void get() {
        Object object = listOfTestObjects.get(0);
        String key = ((TestObject) object).getStringField();
        cache.put(key, object);
        Object expected = cache.get(key);

        assertEquals(expected, object);
    }

    @Test
    public void remove() {
        for (int i = 0; i < 3; i++) {
            Object object = listOfTestObjects.get(i);
            String key = ((TestObject) object).getStringField();
            cache.put(key, object);
        }
        Object object = listOfTestObjects.get(1);
        String key = ((TestObject) object).getStringField();
        cache.remove(key);

        assertEquals(cache.size(), 2);
    }

    @Test
    public void size() {
        for (int i = 0; i < 3; i++) {
            Object object = listOfTestObjects.get(i);
            String key = ((TestObject) object).getStringField();
            cache.put(key, object);
        }

        assertEquals(cache.size(), 3);
    }

    @Test
    public void clear() {
        for (int i = 0; i < 3; i++) {
            Object object = listOfTestObjects.get(i);
            String key = ((TestObject) object).getStringField();
            cache.put(key, object);
        }
        cache.clear();

        assertEquals(cache.size(), 0);
    }

    @Ignore
    @Test
    public void allOfSmall() {
        System.out.println("Test objects:");
        for (int i = 0; i < listOfTestObjects.size(); i++) {
            Object object = listOfTestObjects.get(i);
            System.out.println(object.toString());
            cache.get(String.valueOf(2));
            System.out.println(cache.putAndReturnRemovedObject(String.valueOf(i), object));
        }

        System.out.println("Cache:");
        for (int i = 0; i < listOfTestObjects.size(); i++) {
            System.out.println(cache.get(String.valueOf(i)));
        }

//        assertEquals(cache.size(), 0);
    }

}