package tech.hiphone.commons.service;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class ApplicationContextService implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public Object getBean(String beanName) {
        return applicationContext == null ? null : applicationContext.getBean(beanName);
    }

    public <T> T getBean(Class<T> beanClass) {
        return applicationContext == null ? null : applicationContext.getBean(beanClass);
    }

    public <T> T getBean(String beanName, Class<T> requiredType) {
        return applicationContext == null ? null : applicationContext.getBean(beanName, requiredType);
    }

    public <T> Map<String, T> getBeansOfType(Class<T> requiredType) {
        return applicationContext == null ? null : applicationContext.getBeansOfType(requiredType);
    }

    public Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) {
        return applicationContext == null ? null : applicationContext.getBeansWithAnnotation(annotationType);
    }

    public boolean containsBeanDefinition(String beanName) {
        return applicationContext == null ? false : applicationContext.containsBeanDefinition(beanName);
    }

    public void publishEvent(Object event) {
        applicationContext.publishEvent(event);
    }

}
