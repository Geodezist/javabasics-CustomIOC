package ua.com.bpgdev.customioc.reader.xml;

import org.junit.Test;
import ua.com.bpgdev.customioc.entity.BeanDefinition;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class XmlBeanDefinitionReaderTest {
    private String mockXML = "<beans>\n" +
            "    <bean id=\"userBeanDefinition\" class=\"ua.com.bpgdev.customioc.entity.BeanDefinition\">\n" +
            "        <property name=\"id\" value=\"testBeanDefinitionId\"/>\n" +
            "        <property name=\"className\" value=\"BeanDefinition\"/>\n" +
            "    </bean>\n" +
            "\n" +
            "    <bean id=\"userBean\" class=\"ua.com.bpgdev.customioc.entity.Bean\">\n" +
            "        <property name=\"id\" value=\"userBeanId\"/>\n" +
            "        <property name=\"value\" ref=\"userBeanDefinition\"/>\n" +
            "    </bean>\n" +
            "</beans>";

    @Test
    public void testParseXmlWithMock() {
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader("/src/main/resources/context.xml");
        XmlBeanDefinitionReader mockXmlBeanDefinitionReader = mock(XmlBeanDefinitionReader.class);
        when(mockXmlBeanDefinitionReader.loadFile()).thenReturn(new ByteArrayInputStream(mockXML.getBytes(StandardCharsets.UTF_8)));

        //InputStream inputStream = mockXmlBeanDefinitionReader.loadFile();
        //xmlBeanDefinitionReader.initBeanDefinitions(inputStream);

        List<BeanDefinition> beanDefinitions = xmlBeanDefinitionReader.getBeanDefinitions();

        assertEquals(5, beanDefinitions.size());
        assertEquals("userBeanDefinition", beanDefinitions.get(0).getId());
        assertEquals("userBean", beanDefinitions.get(1).getId());
    }

    @Test
    public void testParseXmlWithFile() {
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader("/src/main/resources/context.xml");

        List<BeanDefinition> beanDefinitions = xmlBeanDefinitionReader.getBeanDefinitions();

        assertEquals(5, beanDefinitions.size());
        assertEquals("userBeanDefinition", beanDefinitions.get(0).getId());
        assertEquals("userBean", beanDefinitions.get(1).getId());
    }
}