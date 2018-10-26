package ua.com.bpgdev.customioc.context;

import ua.com.bpgdev.customioc.entity.Bean;
import ua.com.bpgdev.customioc.entity.BeanDefinition;
import ua.com.bpgdev.customioc.reader.BeanDefinitionReader;
import ua.com.bpgdev.customioc.reader.xml.XmlBeanDefinitionReader;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassPathApplicationContext implements ApplicationContext {
    private static final Logger LOG = LoggerFactory.getLogger(ClassPathApplicationContext.class);
    private static final String LOG_MESSAGE_DELIMITER = "---------------------------------------";
    private StringBuilder logStringBuilder = new StringBuilder();
    private List<Bean> beans;
    private SetFieldStrategy setFieldStrategy;

    public ClassPathApplicationContext(String contextFile) {
        BeanDefinitionReader beanDefinitionReader =
                new XmlBeanDefinitionReader("/src/main/resources/" + contextFile);

        beans = constructBeans(beanDefinitionReader.getBeanDefinitions());
        injectValueDependency(beanDefinitionReader.getBeanDefinitions());
        injectRefDependency(beanDefinitionReader.getBeanDefinitions());
    }

    private List<Bean> constructBeans(List<BeanDefinition> beanDefinitions) {
        try {
            List<Bean> beans = new ArrayList<>();

            LOG.debug(LOG_MESSAGE_DELIMITER);
            LOG.debug("Starting to construct Beans ...");

            for (BeanDefinition beanDefinition : beanDefinitions) {
                Class clazz = Class.forName(beanDefinition.getClassName());
                Object o = clazz.getDeclaredConstructor().newInstance();
                Bean bean = new Bean(beanDefinition.getId(), o);
                beans.add(bean);

                LOG.debug(logStringBuilder.
                        append("Bean with id = \"").
                        append(bean.getId()).
                        append("\" has added to Beans list.").toString());
                clearLogStringBuilder();

            }

            LOG.debug("Constructing Beans has finished successfully.");

            return beans;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException |
                NoSuchMethodException | InvocationTargetException e) {
            LOG.error(e.toString());
            throw new RuntimeException(e);
        }
    }

    private void injectValueDependency(List<BeanDefinition> beanDefinitions) {
        LOG.debug(LOG_MESSAGE_DELIMITER);
        LOG.debug("Starting to inject dependency by value ...");

        setFieldStrategy = new SetValueField();
        injectDependency(beanDefinitions);

        LOG.debug("Injecting dependency by value to Beans has finished successfully.");
    }

    private void injectRefDependency(List<BeanDefinition> beanDefinitions) {
        LOG.debug(LOG_MESSAGE_DELIMITER);
        LOG.debug("Starting to inject dependency by reference ...");

        setFieldStrategy = new SetRefField(beans);
        injectDependency(beanDefinitions);

        LOG.debug("Injecting dependency by reference to Beans has finished successfully.");
    }

    private void injectDependency(List<BeanDefinition> beanDefinitions) {
        try {
            for (BeanDefinition beanDefinition : beanDefinitions) {

                LOG.debug(logStringBuilder.
                        append("Processing ").
                        append(beanDefinition.getId()).
                        append(" bean definition ...").toString());
                clearLogStringBuilder();

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

    private void clearLogStringBuilder() {
        logStringBuilder.delete(0, logStringBuilder.length());
    }
}
