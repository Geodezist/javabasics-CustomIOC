package ua.com.bpgdev.customioc.reader.xml.testentity;

public class MySubSubClass extends MySubClass {
    private int somePrivateInt = 10;
    private String somePrivateString = "SomeStringValue";
    private boolean somePrivateBoolean = true;

    public MySubSubClass() {
    }

    public MySubSubClass(int somePrivateInt, String somePrivateString) {
        this.somePrivateInt = somePrivateInt;
        this.somePrivateString = somePrivateString;
    }

    public int getSomePrivateInt() {
        return somePrivateInt;
    }

    public void setSomePrivateInt(int somePrivateInt) {
        this.somePrivateInt = somePrivateInt;
    }

    public void setSomePrivateString(String somePrivateString) {
        this.somePrivateString = somePrivateString;
    }

    public void setSomePrivateBoolean(boolean somePrivateBoolean) {
        this.somePrivateBoolean = somePrivateBoolean;
    }

    public String getSomePrivateString() {
        return somePrivateString;
    }

    public boolean isSomePrivateBoolean() {
        return somePrivateBoolean;
    }
}
