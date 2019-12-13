import java.util.LinkedHashMap;
import java.util.Map;

public class MemoryStorage<K, T> implements ICacheStorage<K, T> {
    private Map<K, Wrapper<T>> storage;
    private int maxSize;

    public MemoryStorage(int maxSize) {
        this.storage = new LinkedHashMap<K, Wrapper<T>>();
        this.maxSize = maxSize;
    }

    public void add(K key, Wrapper<T> value) {
        if (!isFull()) {
            storage.put(key, value);
        }
    }

    public Wrapper<T> get(K key) {
        return storage.get(key);
    }

    public void remove(K key) {
        storage.remove(key);
    }

    public boolean isFull() {
        return storage.size() >= maxSize;
    }

    public void clear() {
        storage.clear();
    }

    public int size() {
        return storage.size();
    }

    public Map<K, Wrapper<T>> getAll() {
        return storage;
    }
}
