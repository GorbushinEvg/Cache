import java.util.*;

public class LFUStrategy<K,T> implements ICacheStrategy<K, T> {
    public K findKeyForRemoving(ICacheStorage<K, T> storage) {
        K resultKey = null;

        Map<K, Wrapper<T>> mapOfObjects = storage.getAll();

        if (!mapOfObjects.isEmpty()) {
            int minCounter = -1;
            int currentCounter;

            for (Map.Entry<K, Wrapper<T>> entry : mapOfObjects.entrySet()) {
                currentCounter = ((LFUWrapper<T>) entry.getValue()).getCounter();

                if (currentCounter < minCounter || minCounter == -1) {
                    minCounter = currentCounter;
                    resultKey = entry.getKey();
                }
            }
        }

        return resultKey;
    }

    public Wrapper<T> createWrapper(T value) {
        return new LFUWrapper<T>(value);
    }

    public void registerUsage(Wrapper<T> value) {
        LFUWrapper<T> wrapper = (LFUWrapper<T>)value;

        if (wrapper != null) {
            wrapper.setCounter(wrapper.getCounter() + 1);
        }
    }
}

