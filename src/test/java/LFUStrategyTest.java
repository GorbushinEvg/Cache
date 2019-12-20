import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class LFUStrategyTest {
    private List<Wrapper<Object>> listOfWrappers = new ArrayList<Wrapper<Object>>();
    private static final short MAX_SIZE_OF_STORAGE = 10;
    private static final short MAX_SIZE_OF_LISTOFWRAPPERS = MAX_SIZE_OF_STORAGE + 10;

    private ICacheStorage<String, Object> storage = new MemoryStorage<String, Object>(MAX_SIZE_OF_STORAGE);
    private ICacheStrategy<String, Object> strategy = new LFUStrategy<String, Object>();

    @Before
    public void setUp() throws Exception {
        for (int i = 0; i < MAX_SIZE_OF_LISTOFWRAPPERS; i++) {
            TestObject newTestObject = new TestObject(i);
            Wrapper<Object> newWrapper = new LFUWrapper<Object>(newTestObject);
            listOfWrappers.add(newWrapper);
        }
    }

    @Test
    public void findKeyForRemoving() {
        for (int i = 0; i < MAX_SIZE_OF_STORAGE; i++) {
            LFUWrapper<Object> wrapper = (LFUWrapper<Object>) listOfWrappers.get(i);
            if (i == 5) {
                wrapper.setCounter(5);
            } else {
                wrapper.setCounter(1000 - i);
            }
            storage.add(String.valueOf(i), wrapper);
        }
        String actualKey = "5";
        String expectedKey = strategy.findKeyForRemoving(storage);

        assertEquals(expectedKey, actualKey);
    }

    @Test
    public void createWrapper() {
        Wrapper<Object> newWrapper = new LFUWrapper<Object>(new TestObject(0));
        assertNotNull(newWrapper);
    }

    @Test
    public void registerUsage() {
        strategy.registerUsage(listOfWrappers.get(4));
        strategy.registerUsage(listOfWrappers.get(4));
        LFUWrapper<Object> wrapper = (LFUWrapper<Object>)listOfWrappers.get(4);

        assertEquals(wrapper.getCounter(), 2);
    }
}