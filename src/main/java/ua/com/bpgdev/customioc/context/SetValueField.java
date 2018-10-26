package ua.com.bpgdev.customioc.context;

import ua.com.bpgdev.customioc.entity.BeanDefinition;

import java.lang.reflect.Field;
import java.util.Map;

public class SetValueField extends AbstractSetField implements SetFieldStrategy {
    @Override
    public void doInjection(Class clazz, Object object,
                            BeanDefinition beanDefinition) throws IllegalAccessException {
        Map<String, String> valueDependency = beanDefinition.getValueDependency();
        setAllFields(clazz, object, valueDependency);

    }

    @Override
    public void setAllFields(Class clazz, Object object, Map<String, String> mapDependency) throws IllegalAccessException {
        if (clazz.getSuperclass() != null) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                String valueFromDefinition = mapDependency.get(field.getName());
                if (valueFromDefinition != null) {
                    setPrivateAndPublicField(field, object, valueFromDefinition);
                }
            }
            setAllFields(clazz.getSuperclass(), object, mapDependency);
        }
    }
}
