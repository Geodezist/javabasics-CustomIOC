package ua.com.bpgdev.customioc.context;

import ua.com.bpgdev.customioc.entity.Bean;
import ua.com.bpgdev.customioc.entity.BeanDefinition;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class SetRefField extends AbstractSetField implements SetFieldStrategy {
    private List<Bean> beans;

    @Override
    public void doInjection(Object beanObject, BeanDefinition beanDefinition)
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Map<String, String> refDependency = beanDefinition.getRefDependency();
        invokeAllSetters(beanObject, refDependency);
    }

    public void invokeSetter(Object beanObject, Method setterMethod, String propertyValue)
            throws InvocationTargetException, IllegalAccessException {
        Class propertyClass = (setterMethod.getParameterTypes())[0];
        Object propertyObject = getObjectByRefDefinition(propertyValue, propertyClass);
        setterMethod.invoke(beanObject, propertyObject);
    }

    private Object getObjectByRefDefinition(String objectId, Class clazz) {
        for (Bean bean : beans) {
            if (objectId.equals(bean.getId()) && clazz.equals(bean.getValue().getClass())) {
                return bean.getValue();
            }
        }
        return null;
    }

    public SetRefField(List<Bean> beans) {
        this.beans = beans;
    }
}
