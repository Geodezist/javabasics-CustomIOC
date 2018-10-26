package ua.com.bpgdev.customioc.context;

import java.util.List;

public interface  ApplicationContext {
    <T> T getBean(Class<T> clazz);
    <T> T getBean(String id);
    <T> T getBean(String id, Class<T> clazz);
    List<String> getBeanNames();
}
