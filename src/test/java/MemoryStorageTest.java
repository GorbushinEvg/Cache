import org.junit.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class MemoryStorageTest {
    private List<Wrapper<Object>> listOfWrappers = new ArrayList<Wrapper<Object>>();
    private static final short MAX_SIZE_OF_STORAGE = 10;
    private static final short MAX_SIZE_OF_LISTOFWRAPPERS = MAX_SIZE_OF_STORAGE + 10;

    private ICacheStorage<String, Object> storage = new MemoryStorage<String, Object>(MAX_SIZE_OF_STORAGE);

    @Before
    public void setUp() throws Exception {
        for (int i = 0; i < MAX_SIZE_OF_LISTOFWRAPPERS; i++) {
            TestObject newTestObject = new TestObject(i);
            Wrapper<Object> newWrapper = new LFUWrapper<Object>(newTestObject);
            listOfWrappers.add(newWrapper);
        }
    }

    @Test
    public void add() {
        storage.add("0", listOfWrappers.get(0));
        storage.add("1", listOfWrappers.get(1));

        assertEquals(storage.size(), 2);
    }

    @Test
    public void get() {
        storage.add("0", listOfWrappers.get(0));
        storage.add("1", listOfWrappers.get(1));
        storage.add("2", listOfWrappers.get(2));
        Wrapper<Object> expected = storage.get("1");

        assertEquals(expected, listOfWrappers.get(1));
    }

    @Test
    public void remove() {
        storage.add("1", listOfWrappers.get(1));
        storage.add("2", listOfWrappers.get(2));
        storage.add("3", listOfWrappers.get(3));
        storage.remove("2");

        assertEquals(storage.size(), 2);
    }

    @Test
    public void isFull() {
        for (int i = 0; i < MAX_SIZE_OF_STORAGE - 1; i++) {
            storage.add(String.valueOf(i), listOfWrappers.get(i));
        }
        assertFalse(storage.isFull());

        storage.add(String.valueOf(MAX_SIZE_OF_STORAGE), listOfWrappers.get(MAX_SIZE_OF_STORAGE));
        assertTrue(storage.isFull());
    }

    @Test
    public void clear() {
        storage.add("0", listOfWrappers.get(0));
        storage.clear();

        assertEquals(0, storage.size());
    }

    @Test
    public void size() {
        for (int i = 0; i < 5; i++) {
            storage.add(String.valueOf(i), listOfWrappers.get(i));
        }
        assertEquals(5, storage.size());
    }

    @Test
    public void getAll() {
        for (int i = 0; i < 5; i++) {
            storage.add(String.valueOf(i), listOfWrappers.get(i));
        }
        Map<String, Wrapper<Object>> currentListOfWrappers = storage.getAll();

        assertEquals(5, storage.size());
        assertEquals(storage.get("2"), currentListOfWrappers.get("2"));
    }
}