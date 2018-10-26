package ua.com.bpgdev.customioc.reader;

import ua.com.bpgdev.customioc.entity.BeanDefinition;

import java.util.List;

public interface BeanDefinitionReader {
  List<BeanDefinition> getBeanDefinitions();
}
