public class LFUWrapper<T> extends Wrapper<T> {
    private int counter;

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public LFUWrapper() {
    }

    LFUWrapper(T value) {
        super(value);
        this.counter = 0;
    }
}
