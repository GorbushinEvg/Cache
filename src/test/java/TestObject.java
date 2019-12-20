class TestObject {
    private int intField;
    private String stringField;

    TestObject(int value) {
        intField = value;
        stringField = String.valueOf(value);
    }

    @Override
    public String toString() {
        return "intField: " + intField + "; stringField: " + stringField;
    }

    public int getIntField() {
        return intField;
    }

    public String getStringField() {
        return stringField;
    }
}
