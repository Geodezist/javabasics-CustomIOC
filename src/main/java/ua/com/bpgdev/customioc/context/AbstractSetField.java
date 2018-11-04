package ua.com.bpgdev.customioc.context;

import ua.com.bpgdev.customioc.entity.BeanDefinition;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;

abstract class AbstractSetField implements SetFieldStrategy {

    @Override
    public abstract void doInjection(Object object, BeanDefinition beanDefinition)
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException;

    void invokeAllSetters(Object beanObject, Map<String, String> mapDependency)
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class beanObjectClass = beanObject.getClass();
        for (String propertyName : mapDependency.keySet()) {
            Method setterMethod = getSetterMethodName(beanObjectClass, propertyName);
            invokeSetter(beanObject, setterMethod, mapDependency.get(propertyName));
        }
    }

    public abstract void invokeSetter(Object beanObject, Method setterMethod, String propertyValue)
            throws InvocationTargetException, IllegalAccessException;

    private Method getSetterMethodName(Class beanObjectClass, String propertyName) throws NoSuchMethodException {
        String setterMethodName = getSetterName(propertyName);
        Method[] methods = beanObjectClass.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(setterMethodName)) {
                return method;
            }
        }
        throw new NoSuchMethodException("Setter has not been found for field: " + propertyName);
    }

    private String getSetterName(String fieldName) {
        return "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
    }
}
