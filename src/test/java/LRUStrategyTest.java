import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class LRUStrategyTest {
    private List<Wrapper<Object>> listOfWrappers = new ArrayList<Wrapper<Object>>();
    private static final short MAX_SIZE_OF_STORAGE = 10;
    private static final short MAX_SIZE_OF_LISTOFWRAPPERS = MAX_SIZE_OF_STORAGE + 10;

    private ICacheStorage<String, Object> storage = new MemoryStorage<String, Object>(MAX_SIZE_OF_STORAGE);
    private ICacheStrategy<String, Object> strategy = new LRUStrategy<String, Object>();

    @Before
    public void setUp() throws Exception {
        for (int i = 0; i < MAX_SIZE_OF_LISTOFWRAPPERS; i++) {
            TestObject newTestObject = new TestObject(i);
            Wrapper<Object> newWrapper = new LRUWrapper<Object>(newTestObject);
            listOfWrappers.add(newWrapper);
        }
    }

    @Test
    public void findKeyForRemoving() {
        for (int i = 0; i < MAX_SIZE_OF_STORAGE; i++) {
            Calendar cal = Calendar.getInstance();
            if (i == 5) {
                cal.add(Calendar.DATE, -1);
            }
            LRUWrapper<Object> wrapper = (LRUWrapper<Object>) listOfWrappers.get(i);
            wrapper.setLastAccessTime(cal.getTime().getTime());

            storage.add(String.valueOf(i), wrapper);

//            try {
//                Thread.sleep(1100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
        String actualKey = "5";
        String expectedKey = strategy.findKeyForRemoving(storage);

        assertEquals(expectedKey, actualKey);
    }

    @Test
    public void createWrapper() {
        Wrapper<Object> newWrapper = new LRUWrapper<Object>(new TestObject(0));
        assertNotNull(newWrapper);
    }

    @Test
    public void registerUsage() {
        long previousLastAccessTime =((LRUWrapper<Object>)listOfWrappers.get(3)).getLastAccessTime();
        try {
            Thread.sleep(1100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        strategy.registerUsage(listOfWrappers.get(3));
        long currentLastAccessTime =((LRUWrapper<Object>)listOfWrappers.get(3)).getLastAccessTime();

        assertTrue(previousLastAccessTime < currentLastAccessTime);
    }
}