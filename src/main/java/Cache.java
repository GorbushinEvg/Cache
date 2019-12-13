import javafx.util.Pair;

public class Cache<K, T> implements ICache<K, T> {
    private ICacheStorage<K, T> storage;
    private ICacheStrategy<K, T> strategy;

    Cache(ICacheStorage<K, T> storage, ICacheStrategy<K, T> strategy) {
        this.storage = storage;
        this.strategy = strategy;
    }

    public void put(K key, T value) {
        putAndReturnRemovedObject(key, value);
    }

    public Pair<K, T> putAndReturnRemovedObject(K key, T value) {
        Pair<K, T> objectForRemoving = null;

        Wrapper<T> wrapper = storage.get(key);

        if (wrapper == null) {
            if (storage.isFull()) {
                K keyForRemoving = strategy.findKeyForRemoving(storage);
                Wrapper<T> wrapperForRemoving = storage.get(keyForRemoving);
                objectForRemoving = new Pair<K, T>(keyForRemoving, wrapperForRemoving.getValue());
                storage.remove(keyForRemoving);
            }
        } else
            strategy.registerUsage(wrapper);

        Wrapper<T> wrapperNew = strategy.createWrapper(value);
        storage.add(key, wrapperNew);

        return objectForRemoving;
    }

    public T get(K key) {
        Wrapper<T> wrapper = storage.get(key);

        if (wrapper != null) {
            strategy.registerUsage(wrapper);
            return wrapper.getValue();
        } else
            return null;
    }

    public void remove(K key) {
        storage.remove(key);
    }

    public int size() {
        return storage.size();
    }

    public void clear() {
        storage.clear();
    }
}
