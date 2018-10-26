package ua.com.bpgdev.customioc.entity;

public class Bean {
    private String id;
    private Object value;

    public Bean(String id, Object value) {
        this.id = id;
        this.value = value;
    }

    public Bean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "id='" + id + '\'' +
                ", value=" + value +
                '}';
    }
}
