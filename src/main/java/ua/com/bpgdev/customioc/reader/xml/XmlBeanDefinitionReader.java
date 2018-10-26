package ua.com.bpgdev.customioc.reader.xml;

import ua.com.bpgdev.customioc.entity.BeanDefinition;
import ua.com.bpgdev.customioc.reader.BeanDefinitionReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class XmlBeanDefinitionReader implements BeanDefinitionReader {
    private String path;
    private List<BeanDefinition> beanDefinitions;

    public XmlBeanDefinitionReader(String path) {
        this.path = path;
    }

    public List<BeanDefinition> getBeanDefinitions() {
        if (beanDefinitions == null) {
            beanDefinitions = initBeanDefinitions(loadFile());
        }
        return beanDefinitions;
    }

    List<BeanDefinition> initBeanDefinitions(InputStream inputStream) {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        BeanDefinitionXmlHandler beanDefinitionXmlHandler = new BeanDefinitionXmlHandler();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            saxParser.parse(inputStream, beanDefinitionXmlHandler);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return beanDefinitionXmlHandler.getBeanDefinitions();
    }

    InputStream loadFile() {
        Path filePath = Paths.get(".", path);
        String absoluteFileName = filePath.normalize().toAbsolutePath().toString();
        File file = new File(absoluteFileName);
        try {
            return file.isFile() ?
                    new FileInputStream(file) :
                    this.getClass().getResourceAsStream(path);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read context file", e);
        }
    }
}
