package com.example;

import com.example.config.AppConfig;
import com.example.service.UserService;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserTest {

    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    private UserService service = applicationContext.getBean("UserService", UserService.class);;

    @Test
    public void testGetId() throws IOException, ParseException {
        assertEquals(1, service.list().get((long)1).getId());
    }

    @Test
    public void testGetFirstName() throws IOException, ParseException {
        assertEquals("John", service.list().get((long)1).getFirstName());
    }

    @Test
    public void testGetLastName() throws IOException, ParseException {
        assertEquals("Johnson", service.list().get((long)1).getLastName());
    }

    @Test
    public void testGetUsername() throws IOException, ParseException {
        assertEquals("john.johnson", service.list().get((long)1).getUserName());
    }

    @Test
    public void testGetPassword() throws IOException, ParseException {
        assertEquals("password", service.list().get((long)1).getPassword());
    }

    @Test
    public void testIsIsActive() throws IOException, ParseException {
        assertTrue(service.list().get((long)1).isActive());
    }
}
