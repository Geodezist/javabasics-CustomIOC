package ua.com.bpgdev.customioc.reader.xml;

import org.junit.Before;
import org.junit.Test;
import ua.com.bpgdev.customioc.entity.BeanDefinition;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.Assert.*;

public class XmlBeanDefinitionReaderTest {
    private InputStream mockInputStream;

    @Before
    public void prepare() {
        mockInputStream = new ByteArrayInputStream(XmlMockData.getMockXML().getBytes(StandardCharsets.UTF_8));
    }

    @Test
    public void testParseXmlWithMock() {
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader();
        xmlBeanDefinitionReader.initBeanDefinitions(mockInputStream);

        List<BeanDefinition> beanDefinitions = xmlBeanDefinitionReader.getBeanDefinitions();

        assertEquals(5, beanDefinitions.size());
        assertEquals("userBeanDefinition", beanDefinitions.get(0).getId());
        assertEquals("userBean", beanDefinitions.get(1).getId());
    }

}