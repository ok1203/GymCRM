package com.example;

import com.example.config.AppConfig;
import com.example.service.TrainerService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertEquals;

public class TrainerTest {

    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    private TrainerService service = applicationContext.getBean("TrainerService", TrainerService.class);

    @Test
    public void testGetId() {
        assertEquals(1, service.list().get((long)1).getId());
    }

    @Test
    public void testGetSpecializationId() {
        assertEquals(1, service.list().get((long)1).getSpecializationId());
    }

    @Test
    public void testGetUserId() {
        assertEquals(1, service.list().get((long)1).getUserId());
    }
}
