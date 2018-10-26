package ua.com.bpgdev.customioc.context;

import ua.com.bpgdev.customioc.entity.Bean;
import ua.com.bpgdev.customioc.entity.BeanDefinition;
import ua.com.bpgdev.customioc.reader.BeanDefinitionReader;
import ua.com.bpgdev.customioc.reader.xml.XmlBeanDefinitionReader;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ClassPathApplicationContext implements ApplicationContext {
    private BeanDefinitionReader beanDefinitionReader;
    private List<Bean> beans;
    private SetFieldStrategy setFieldStrategy;

    public ClassPathApplicationContext(String contextFile) {
        beanDefinitionReader = new XmlBeanDefinitionReader("/src/main/resources/" + contextFile);

        beans = constructBeans(beanDefinitionReader.getBeanDefinitions());
        injectValueDependency(beanDefinitionReader.getBeanDefinitions());
        injectRefDependency(beanDefinitionReader.getBeanDefinitions());
    }

    public List<Bean> constructBeans(List<BeanDefinition> beanDefinitions) {
        try {
            List<Bean> beans = new ArrayList<>();
            for (BeanDefinition beanDefinition : beanDefinitions) {
                Class clazz = Class.forName(beanDefinition.getClassName());
                Object o = clazz.getDeclaredConstructor().newInstance();
                Bean bean = new Bean(beanDefinition.getId(), o);
                beans.add(bean);
            }
            return beans;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException |
                NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private void injectDependency(List<BeanDefinition> beanDefinitions) {
        try {
            for (BeanDefinition beanDefinition : beanDefinitions) {
                for (Bean bean : beans) {
                    if (bean.getId().equals(beanDefinition.getId())
                            && bean.getValue().getClass().getName().equals(beanDefinition.getClassName())
                    ) {
                        Class clazz = bean.getValue().getClass();
                        // Here is Strategy pattern
                        setFieldStrategy.doInjection(clazz, bean.getValue(), beanDefinition);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void injectValueDependency(List<BeanDefinition> beanDefinitions) {
        setFieldStrategy = new SetValueField();
        injectDependency(beanDefinitions);
    }

    private void injectRefDependency(List<BeanDefinition> beanDefinitions) {
        setFieldStrategy = new SetRefField(beans);
        injectDependency(beanDefinitions);
    }

    public <T> T getBean(Class<T> clazz) {
        for (Bean bean : beans) {
            String beanClassName = bean.getValue().getClass().getName().toString();
            String clazzName = clazz.getName();
            if (beanClassName.equals(clazzName)) {
                return (T) bean.getValue();
            }
        }
        return null;
    }

    public <T> T getBean(String id) {
        for (Bean bean : beans) {
            if (bean.getId().equals(id)) {
                return (T) bean.getValue();
            }
        }
        return null;
    }

    public <T> T getBean(String id, Class<T> clazz) {
        for (Bean bean : beans) {
            if (bean.getId().equals(id) &&
                    bean.getValue().getClass().toString().equals(clazz.getName())) {
                return (T) bean.getValue();
            }
        }
        return null;
    }

    public List<String> getBeanNames() {
        List<String> result = new ArrayList<>();
        for (Bean bean :
                beans) {
            result.add(bean.getId());
        }
        return result;
    }
}
