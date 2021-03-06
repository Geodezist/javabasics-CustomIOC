package ua.com.bpgdev.customioc.reader.xml.testentity;

import java.io.Serializable;

public class MySubClass extends MyClass implements Serializable, Comparable {

    private MySubClass somePrivateMySubClass;
    private double somePrivateDouble = 19d;
    private Float somePrivateFloat = 111f;

    public void setSomePrivateMySubClass(MySubClass somePrivateMySubClass) {
        this.somePrivateMySubClass = somePrivateMySubClass;
    }

    public void setSomePrivateDouble(double somePrivateDouble) {
        this.somePrivateDouble = somePrivateDouble;
    }

    public void setSomePrivateFloat(Float somePrivateFloat) {
        this.somePrivateFloat = somePrivateFloat;
    }

    public MySubClass getSomePrivateMySubClass() {
        return somePrivateMySubClass;
    }

    public double getSomePrivateDouble() {
        return somePrivateDouble;
    }

    public Float getSomePrivateFloat() {
        return somePrivateFloat;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
