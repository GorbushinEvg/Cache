import java.util.Date;

class LRUWrapper<T> extends Wrapper<T> {
    private long lastAccessTime;

    long getLastAccessTime() {
        return lastAccessTime;
    }

    void setLastAccessTime(long lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    LRUWrapper(T value) {
        super(value);
        this.lastAccessTime = new Date().getTime();
    }
}
