package ua.com.bpgdev.customioc;

import ua.com.bpgdev.customioc.context.ApplicationContext;
import ua.com.bpgdev.customioc.context.ClassPathApplicationContext;

public class Starter {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathApplicationContext("context.xml");
        System.out.println(applicationContext.getBeanNames().toString());
        System.out.println("Stop");
    }
}
