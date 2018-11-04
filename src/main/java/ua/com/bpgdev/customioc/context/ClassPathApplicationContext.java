package ua.com.bpgdev.customioc.context;

import ua.com.bpgdev.customioc.entity.Bean;
import ua.com.bpgdev.customioc.entity.BeanDefinition;
import ua.com.bpgdev.customioc.exception.NonUniqueClassException;
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
    private List<Bean> beans;


    public ClassPathApplicationContext(String contextFile) {
        BeanDefinitionReader beanDefinitionReader =
                new XmlBeanDefinitionReader(contextFile);

        beans = constructBeans(beanDefinitionReader.getBeanDefinitions());
        injectValueDependency(beanDefinitionReader.getBeanDefinitions());
        injectRefDependency(beanDefinitionReader.getBeanDefinitions());
    }

    private List<Bean> constructBeans(List<BeanDefinition> beanDefinitions) {
        try {
            List<Bean> beans = new ArrayList<>();

            LOG.info(LOG_MESSAGE_DELIMITER);
            LOG.info("Starting to construct Beans ...");

            for (BeanDefinition beanDefinition : beanDefinitions) {
                Class clazz = Class.forName(beanDefinition.getClassName());
                Object newObject = clazz.getDeclaredConstructor().newInstance();
                Bean bean = new Bean(beanDefinition.getId(), newObject);
                beans.add(bean);

                LOG.debug("Bean with id = \"{}\" has added to Beans list.", bean.getId());

            }

            LOG.info("Constructing Beans has finished successfully.");

            return beans;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException |
                NoSuchMethodException | InvocationTargetException e) {
            LOG.error("Error during beans creation", e);
            throw new RuntimeException(e);
        }
    }

    private void injectValueDependency(List<BeanDefinition> beanDefinitions) {
        LOG.info(LOG_MESSAGE_DELIMITER);
        LOG.info("Starting to inject dependency by value ...");

        SetFieldStrategy setFieldStrategy = new SetValueField();
        injectDependency(beanDefinitions, setFieldStrategy);

        LOG.info("Injecting dependency by value to Beans has finished successfully.");
    }

    private void injectRefDependency(List<BeanDefinition> beanDefinitions) {
        LOG.info(LOG_MESSAGE_DELIMITER);
        LOG.info("Starting to inject dependency by reference ...");

        SetFieldStrategy setFieldStrategy = new SetRefField(beans);
        injectDependency(beanDefinitions, setFieldStrategy);

        LOG.info("Injecting dependency by reference to Beans has finished successfully.");
    }

    private void injectDependency(List<BeanDefinition> beanDefinitions, SetFieldStrategy setFieldStrategy) {
        try {
            for (BeanDefinition beanDefinition : beanDefinitions) {

                LOG.debug("Processing {}  bean definition ...", beanDefinition.getId());

                for (Bean bean : beans) {
                    if (bean.getId().equals(beanDefinition.getId())) {
                        // Here is Strategy pattern
                        setFieldStrategy.doInjection(bean.getValue(), beanDefinition);
                    }
                }
            }
        } catch (IllegalAccessException | NoSuchMethodException e) {
            LOG.error("Error during injecting beans dependencies", e);
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public <T> T getBean(Class<T> clazz) {
        int beanCount = 0;
        T result = null;
        for (Bean bean : beans) {
            if (bean.getValue().getClass() == clazz) {
                beanCount++;
                result = clazz.cast(bean.getValue());
            }
        }
        if (beanCount == 1) {
            return result;
        }
        NonUniqueClassException e = new NonUniqueClassException("Object of Class - \"" + clazz.getName() +
                "\" is not unique in current configuration!");
        LOG.error("", e);
        throw e;

    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(String id) {
        int beanCount = 0;
        T result = null;
        for (Bean bean : beans) {
            if (bean.getId().equals(id)) {
                beanCount++;
                result = (T) bean.getValue();
            }
        }
        if (beanCount == 1) {
            return result;
        }
        NonUniqueClassException e = new NonUniqueClassException("Object with id - \"" + id +
                "\" is not unique in current configuration!");
        LOG.error("", e);
        throw e;
    }

    public <T> T getBean(String id, Class<T> clazz) {
        int beanCount = 0;
        T result = null;
        for (Bean bean : beans) {
            if (bean.getId().equals(id) && bean.getValue().getClass() == clazz) {
                beanCount++;
                result = clazz.cast(bean.getValue());
            }
        }
        if (beanCount == 1) {
            return result;
        }
        NonUniqueClassException e = new NonUniqueClassException("Object with id - \"" + id +
                "\" and class - \"" + clazz.getName() + "\" is not unique in current configuration!");
        LOG.error("", e);
        throw e;
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
