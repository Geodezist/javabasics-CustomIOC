package ua.com.bpgdev.customioc.context;

import ua.com.bpgdev.customioc.entity.BeanDefinition;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

public abstract class AbstractSetField implements SetFieldStrategy {
    @Override
    public abstract void doInjection(Class clazz, Object object, BeanDefinition beanDefinition)
            throws IllegalAccessException;

    public abstract void setAllFields(Class clazz, Object object, Map<String, String> mapDependency)
            throws IllegalAccessException;

    void setPrivateAndPublicField(Field field, Object object, Object valueFromDefinition)
            throws IllegalAccessException {

        if (Modifier.isPrivate(field.getModifiers())) {
            field.setAccessible(true);
            setField(object, field, valueFromDefinition);
            field.setAccessible(false);
        } else {
            setField(object, field, valueFromDefinition);
        }
    }

    private void setField(Object object, Field field, Object value) throws IllegalAccessException {
        switch (field.getGenericType().getTypeName().toLowerCase()) {
            case "char":
                field.set(object, ((String) value).charAt(0));
                break;
            case "character":
                field.set(object, ((String) value).charAt(0));
                break;
            case "byte":
                field.set(object, Byte.valueOf(((String) value)));
                break;
            case "short":
                field.set(object, Short.valueOf(((String) value)));
                break;
            case "int":
                field.set(object, Integer.valueOf(((String) value)));
                break;
            case "integer":
                field.set(object, Integer.valueOf(((String) value)));
                break;
            case "long":
                field.set(object, Long.valueOf(((String) value)));
                break;
            case "float":
                field.set(object, Float.valueOf(((String) value)));
                break;
            case "double":
                field.set(object, Double.valueOf(((String) value)));
                break;
            case "boolean":
                field.set(object, Boolean.valueOf(((String) value)));
                break;
            case "java.lang.string":
                field.set(object, value.toString());
            default:
                field.set(object, value);
        }
    }

}
