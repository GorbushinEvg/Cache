class L2Cache<K, T> extends Cache<K, T> {
    L2Cache(ICacheStrategy<K, T> strategy, int maxSize) {
        super(new MemoryStorage<K, T>(maxSize), strategy);
    }
}