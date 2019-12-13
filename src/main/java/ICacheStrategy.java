public interface ICacheStrategy<K, T> {
    K findKeyForRemoving(ICacheStorage<K, T> storage);
    Wrapper<T> createWrapper(T value);
    void registerUsage(Wrapper<T> value);
}
