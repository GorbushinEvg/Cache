import java.util.Date;

public class LRUWrapper<T> extends Wrapper<T> {
    private Date lastAccessTime;

    public Date getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Date lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    LRUWrapper(T value) {
        super(value);
        this.lastAccessTime = new Date();
    }
}
