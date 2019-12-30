//import java.io.Serializable;
//import java.util.Objects;

class TestObject {
//class TestObject {
    private int intField;
    private String stringField;

    TestObject(int value) {
        intField = value;
        stringField = String.valueOf(value);
    }

    public TestObject() {
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

    public void setIntField(int intField) {
        this.intField = intField;
    }

    public void setStringField(String stringField) {
        this.stringField = stringField;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestObject that = (TestObject) o;
        return intField == that.intField &&
                stringField.equals(that.stringField);
    }
}
