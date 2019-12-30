import java.io.Serializable;

abstract class Wrapper<T> {
    private T value;

    Wrapper() {
        value = (T) (new Object());
    }

    T getValue() {
        return this.value;
    }

    Wrapper(T value) {
        this.value = value;
    }

    void setValue(T value) {
        this.value = value;
    }
}
