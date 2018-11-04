package ua.com.bpgdev.customioc.context;

import ua.com.bpgdev.customioc.entity.BeanDefinition;

import java.lang.invoke.WrongMethodTypeException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class SetValueField extends AbstractSetField implements SetFieldStrategy {

    @Override
    public void doInjection(Object beanObject, BeanDefinition beanDefinition)
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Map<String, String> valueDependency = beanDefinition.getValueDependency();
        invokeAllSetters(beanObject, valueDependency);
    }

    @Override
    public void invokeSetter(Object beanObject, Method setterMethod, String propertyValue)
            throws InvocationTargetException, IllegalAccessException {

        Class genericType = (setterMethod.getParameterTypes())[0];

        if (genericType == char.class || genericType == Character.class) {
            setterMethod.invoke(beanObject, propertyValue.charAt(0));
        } else if (genericType == byte.class) {
            setterMethod.invoke(beanObject, Byte.parseByte(propertyValue));
        } else if (genericType == short.class) {
            setterMethod.invoke(beanObject, Short.parseShort(propertyValue));
        } else if (genericType == int.class || genericType == Integer.class) {
            setterMethod.invoke(beanObject, Integer.parseInt(propertyValue));
        } else if (genericType == long.class) {
            setterMethod.invoke(beanObject, Long.parseLong(propertyValue));
        } else if (genericType == float.class) {
            setterMethod.invoke(beanObject, Float.parseFloat(propertyValue));
        } else if (genericType == double.class) {
            setterMethod.invoke(beanObject, Double.parseDouble(propertyValue));
        } else if (genericType == boolean.class) {
            setterMethod.invoke(beanObject, Boolean.parseBoolean(propertyValue));
        } else if (genericType == java.lang.String.class) {
            setterMethod.invoke(beanObject, propertyValue);
        } else {
            throw new WrongMethodTypeException();
        }

    }

}
