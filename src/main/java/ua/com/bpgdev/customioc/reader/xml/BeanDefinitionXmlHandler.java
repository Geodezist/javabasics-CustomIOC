package ua.com.bpgdev.customioc.reader.xml;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import ua.com.bpgdev.customioc.entity.BeanDefinition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BeanDefinitionXmlHandler extends DefaultHandler {
    private List<BeanDefinition> beanDefinitions;
    private BeanDefinition beanDefinition;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if ("bean".equalsIgnoreCase(qName)) {
            beanDefinition = new BeanDefinition();
            beanDefinition.setValueDependency(new HashMap<>());
            beanDefinition.setRefDependency(new HashMap<>());

            beanDefinition.setId(attributes.getValue("id"));
            beanDefinition.setClassName(attributes.getValue("class"));
            if (beanDefinitions == null) {
                beanDefinitions = new ArrayList<>();
            }
        } else if ("property".equalsIgnoreCase(qName)) {
            String propertyName = attributes.getValue("name");
            String valueDependency = attributes.getValue("value");
            String refDependency = attributes.getValue("ref");
            if(valueDependency != null){
                beanDefinition.getValueDependency().put(propertyName, valueDependency);
            } else {
                beanDefinition.getRefDependency().put(propertyName, refDependency);
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if("bean".equalsIgnoreCase(qName)){
            beanDefinitions.add(beanDefinition);
        }
    }

    public List<BeanDefinition> getBeanDefinitions() {
        return beanDefinitions;
    }

}
