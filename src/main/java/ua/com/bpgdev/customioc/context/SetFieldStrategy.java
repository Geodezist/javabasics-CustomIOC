package ua.com.bpgdev.customioc.context;

import ua.com.bpgdev.customioc.entity.BeanDefinition;

import java.lang.reflect.InvocationTargetException;

public interface SetFieldStrategy {

    void doInjection(Object beanObject,
                     BeanDefinition beanDefinition) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException;

}
