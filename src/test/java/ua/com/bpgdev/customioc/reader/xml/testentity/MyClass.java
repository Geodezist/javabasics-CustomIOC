package ua.com.bpgdev.customioc.reader.xml.testentity;

public class MyClass {
    private int count;
    private String name;
    public double myClassDouble;


    public MyClass() {
    }

    public MyClass(String name) {
        this.name = name;
    }

    public void printMyClassName() {
        System.err.println(name);
    }

    public void printMyClass() {
        System.err.println(toString());
    }

    public final int getIntNumber() {
        return 5;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMyClassDouble(double myClassDouble) {
        this.myClassDouble = myClassDouble;
    }

    private void somePrivateMethod() {

    }

    @Override
    public String toString() {
        return "ua.com.bpgdev.reflection.myclass.MyClass{" +
                "count=" + count +
                ", name='" + name + '\'' +
                ", myClassDouble=" + myClassDouble +
                '}';
    }
}
