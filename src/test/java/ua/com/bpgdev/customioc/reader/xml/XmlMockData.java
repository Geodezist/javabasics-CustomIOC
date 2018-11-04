package ua.com.bpgdev.customioc.reader.xml;

public class XmlMockData {
    private final static String XML_MOCK_DATA = "<beans>\n" +
            "    <bean id=\"userBeanDefinition\" class=\"ua.com.bpgdev.customioc.entity.BeanDefinition\">\n" +
            "        <property name=\"id\" value=\"testBeanDefinitionId\"/>\n" +
            "        <property name=\"className\" value=\"BeanDefinition\"/>\n" +
            "    </bean>\n" +
            "\n" +
            "    <bean id=\"userBean\" class=\"ua.com.bpgdev.customioc.entity.Bean\">\n" +
            "        <property name=\"id\" value=\"userBeanId\"/>\n" +
            "        <property name=\"value\" ref=\"userBeanDefinition\"/>\n" +
            "    </bean>\n" +
            "\n" +
            "    <bean id=\"testMyClass\" class=\"ua.com.bpgdev.customioc.reader.xml.testentity.MyClass\">\n" +
            "        <property name=\"count\" value=\"10\"/>\n" +
            "        <property name=\"name\" value=\"testMyClass.nameProperty\"/>\n" +
            "        <property name=\"myClassDouble\" value=\"10.01\"/>\n" +
            "    </bean>\n" +
            "\n" +
            "    <bean id=\"testMySubClass\" class=\"ua.com.bpgdev.customioc.reader.xml.testentity.MySubClass\">\n" +
            "        <property name=\"count\" value=\"100\"/>\n" +
            "        <property name=\"name\" value=\"testMySubClass.nameProperty\"/>\n" +
            "        <property name=\"myClassDouble\" value=\"100.001\"/>\n" +
            "        <property name=\"somePrivateMySubClass\" ref=\"testMySubClass\"/>\n" +
            "        <property name=\"somePrivateDouble\" value=\"200.002\"/>\n" +
            "    </bean>\n" +
            "\n" +
            "    <bean id=\"testMySubSubClass\" class=\"ua.com.bpgdev.customioc.reader.xml.testentity.MySubSubClass\">\n" +
            "        <property name=\"count\" value=\"1000\"/>\n" +
            "        <property name=\"name\" value=\"testMySubSubClass.nameProperty\"/>\n" +
            "        <property name=\"myClassDouble\" value=\"1000.0001\"/>\n" +
            "        <property name=\"somePrivateMySubClass\" ref=\"testMySubClass\"/>\n" +
            "        <property name=\"somePrivateDouble\" value=\"2000.0002\"/>\n" +
            "        <property name=\"somePrivateInt\" value=\"4000\"/>\n" +
            "        <property name=\"somePrivateString\" value=\"SomeStringValue\"/>\n" +
            "        <property name=\"somePrivateBoolean\" value=\"false\"/>\n" +
            "    </bean>\n" +
            "</beans>";

    public static String getMockXML() {
        return XML_MOCK_DATA;
    }
}
