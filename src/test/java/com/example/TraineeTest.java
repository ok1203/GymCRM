package com.example;

import com.example.config.AppConfig;
import com.example.service.TraineeService;
import com.example.service.UserService;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

public class TraineeTest {

    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    private TraineeService service = applicationContext.getBean("TraineeService", TraineeService.class);

    @Test
    public void testGetId() throws IOException, ParseException {
        assertEquals(1, service.list().get((long)1).getId());
    }

    @Test
    public void testGetDateOfBirth() throws IOException, ParseException {
        Date dateOfBirth = service.list().get((long)1).getDateOfBirth();
        assertEquals(2000, dateOfBirth.getYear());
        assertEquals(11, dateOfBirth.getMonth());
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(dateOfBirth);
        assertEquals(11, calendar.DAY_OF_MONTH);
    }

    @Test
    public void testGetAddress() throws IOException, ParseException {
        assertEquals("Backer St. 221b", service.list().get((long)1).getAddress());
    }

    @Test
    public void testGetUserId() throws IOException, ParseException {
        assertEquals(1, service.list().get((long)1).getUserId());
    }
}
