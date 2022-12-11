package com.example.CashMashine.BPP;

import com.example.CashMashine.annotation.BundleResource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

@Component
public class BundleResourceInjectBeanPostProcessor implements BeanPostProcessor {


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field s : fields) {
            Annotation annotation = s.getAnnotation(BundleResource.class);
            if (annotation != null) {
                s.setAccessible(true);
                ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
                resourceBundleMessageSource.setBasename(((BundleResource) annotation).name());
                ReflectionUtils.setField(s, bean, resourceBundleMessageSource);
            }
        }
        return bean;
    }
}
