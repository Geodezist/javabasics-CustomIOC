package ua.com.bpgdev.customioc.context;

import ua.com.bpgdev.customioc.entity.BeanDefinition;

public interface SetFieldStrategy {

    void doInjection(Class clazz, Object object,
                     BeanDefinition beanDefinition) throws IllegalAccessException;

}
