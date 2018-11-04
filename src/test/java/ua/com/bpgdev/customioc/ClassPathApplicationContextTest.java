package ua.com.bpgdev.customioc;

import org.junit.Before;
import org.junit.Test;
import ua.com.bpgdev.customioc.context.ApplicationContext;
import ua.com.bpgdev.customioc.context.ClassPathApplicationContext;
import ua.com.bpgdev.customioc.reader.xml.testentity.MySubClass;
import ua.com.bpgdev.customioc.reader.xml.testentity.MySubSubClass;

import static org.junit.Assert.*;

public class ClassPathApplicationContextTest {
    private ApplicationContext applicationContext;

    @Before
    public void prepare() {
        applicationContext = new ClassPathApplicationContext("/src/test/resources/context.xml");
    }

    @Test
    public void testConstructBeans() {
        assertEquals(5, applicationContext.getBeanNames().size());
    }

    @Test
    public void testGetBeanByClass() throws ClassNotFoundException {
        Class clazz = Class.forName("ua.com.bpgdev.customioc.reader.xml.testentity.MySubSubClass");
        MySubSubClass mySubSubClass = (MySubSubClass) applicationContext.getBean(clazz);

        assertEquals("SomeStringValue", mySubSubClass.getSomePrivateString());
    }

    @Test
    public void testGetBeanById() {
        String beanId = "testMySubClass";
        MySubClass mySubClass = applicationContext.getBean(beanId);

        assertEquals(200.002d, mySubClass.getSomePrivateDouble(),0);
    }

    @Test
    public void testGetBeanByIdAndClass() {

    }


    @Test
    public void testGetBeanNames() {

    }

}