package com.example.annotation.bpp;

import com.example.annotation.Storage;
import com.example.storage.StorageComponent;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.util.Arrays;

@Component
public class StorageBeanPostProcessor implements BeanPostProcessor {
    private static final Logger log = LoggerFactory.getLogger(StorageBeanPostProcessor.class);
    private static StorageComponent storageComponent = null;

    static {
        try {
            storageComponent = new StorageComponent();
        } catch (IOException e) {
            log.error("IOExeption caught, cannot operate file");
            e.printStackTrace();
        } catch (ParseException e) {
            log.error("unexpected condition or unknown error");
            e.printStackTrace();
        }
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        log.debug("Starting storage injection");
        Class<?> beanClass = bean.getClass();
        Arrays.stream(beanClass.getDeclaredFields()) // going through all the fields of the class
                .filter(field -> field.isAnnotationPresent(Storage.class)) // check if each of fields annotated with @InjectRandomEntity
                .forEach(field -> {
                    field.setAccessible(true); // make the field accessible to set a value
                    ReflectionUtils.setField(field, bean, storageComponent); // set the value in the object
                });
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
