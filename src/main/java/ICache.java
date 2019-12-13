import javafx.util.Pair;

public interface ICache<K, T> {
    void put(K key, T value);
    Pair<K, T> putAndReturnRemovedObject(K key, T value);
    T get(K key);
    void remove(K key);
    int size();
    void clear();
}