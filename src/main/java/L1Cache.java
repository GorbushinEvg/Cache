class L1Cache<K, T> extends Cache<K, T> {
    L1Cache(ICacheStrategy<K, T> strategy, int maxSize) {
        super(new MemoryStorage<K, T>(maxSize), strategy);
    }
}
