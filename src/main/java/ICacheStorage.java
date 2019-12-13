import java.util.Map;

public interface ICacheStorage<K, T> {
    void add(K key, Wrapper<T> value);
    Wrapper<T> get(K key);
    void remove(K key);
    boolean isFull();
    Map<K, Wrapper<T>> getAll();
    void clear();
    int size();
}
