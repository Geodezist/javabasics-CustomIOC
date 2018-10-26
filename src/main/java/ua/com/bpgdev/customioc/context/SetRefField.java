package ua.com.bpgdev.customioc.context;

import ua.com.bpgdev.customioc.entity.Bean;
import ua.com.bpgdev.customioc.entity.BeanDefinition;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class SetRefField extends AbstractSetField implements SetFieldStrategy {
    private List<Bean> beans;

    @Override
    public void doInjection(Class clazz, Object object,
                            BeanDefinition beanDefinition) throws IllegalAccessException {

        Map<String, String> refDependency = beanDefinition.getRefDependency();
        setAllFields(clazz, object, refDependency);
    }

    @Override
    public void setAllFields(Class clazz, Object object, Map<String, String> mapDependency) throws IllegalAccessException {
        if (clazz.getSuperclass() != null) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                String refFromDefinition = mapDependency.get(field.getName());
                if (refFromDefinition != null) {
                    Object refObject = getObjectByRefDefinition(refFromDefinition, clazz);
                    setPrivateAndPublicField(field, object, refObject);
                }
            }
            setAllFields(clazz.getSuperclass(), object, mapDependency);
        }
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
