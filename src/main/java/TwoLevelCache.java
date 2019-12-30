//import javafx.util.Pair;

public class TwoLevelCache<K, T> implements ICache<K, T> {

    private ICache<K, T> firstLevelCache;
    private ICache<K, T> secondLevelCache;

    TwoLevelCache(ICache<K, T> firstLevelCache, ICache<K, T> secondLevelCache) {
        this.firstLevelCache = firstLevelCache;
        this.secondLevelCache = secondLevelCache;
    }

    public void put(K key, T value) {
        putAndReturnRemovedObject(key, value);
    }

    public Pair<K, T> putAndReturnRemovedObject(K key, T value) {
        Pair<K, T> objectForRemoving;

        boolean isExistsKeyInL2 = secondLevelCache.get(key) != null;

        if (isExistsKeyInL2) {
            secondLevelCache.remove(key);

            objectForRemoving = firstLevelCache.putAndReturnRemovedObject(key, value);

            if (objectForRemoving != null) {
                objectForRemoving = secondLevelCache.putAndReturnRemovedObject(objectForRemoving.getKey(), objectForRemoving.getValue());
            }
        } else {
            boolean isExistsKeyInL1 = firstLevelCache.get(key) != null;

            if (isExistsKeyInL1) {
                firstLevelCache.remove(key);
            }

            objectForRemoving = firstLevelCache.putAndReturnRemovedObject(key, value);
        }
        return objectForRemoving;
    }

    public T get(K key) {
        T value;
        value = firstLevelCache.get(key);

        if (value == null) {
            value = secondLevelCache.get(key);
        }
        return value;
    }

    public void remove(K key) {
        firstLevelCache.remove(key);
        secondLevelCache.remove(key);
    }

    public int size() {
        return firstLevelCache.size() + secondLevelCache.size();
    }

    public void clear() {
        firstLevelCache.clear();
        secondLevelCache.clear();
    }
}
