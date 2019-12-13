abstract class Wrapper<T> {
    private T value;

    T getValue() {
        return this.value;
    }

    Wrapper(T value) {
        this.value = value;
    }
}
