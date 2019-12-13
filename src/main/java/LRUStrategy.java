import java.util.*;

public class LRUStrategy<K,T> implements ICacheStrategy<K, T> {
    public K findKeyForRemoving(ICacheStorage<K, T> storage) {
        K resultKey = null;

        Map<K, Wrapper<T>> mapOfObjects = storage.getAll();

        if (!mapOfObjects.isEmpty()) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, 1);
            Date minAccessTime = cal.getTime();
            Date currentAccessTime;

            for (Map.Entry<K, Wrapper<T>> entry : mapOfObjects.entrySet()) {
                currentAccessTime = ((LRUWrapper<T>) entry.getValue()).getLastAccessTime();

                if (currentAccessTime.getTime() < minAccessTime.getTime()) {
                    minAccessTime = currentAccessTime;
                    resultKey = entry.getKey();
                }
            }
        }

        return resultKey;
    }

    public Wrapper<T> createWrapper(T value) {
        return new LRUWrapper<T>(value);
    }

    public void registerUsage(Wrapper<T> value) {
        LRUWrapper<T> wrapper = (LRUWrapper<T>)value;

        if (wrapper != null) {
            wrapper.setLastAccessTime(new Date());
        }
    }
}
