package com.example;

import com.example.service.TraineeService;
import com.example.service.TrainerService;
import com.example.service.UserService;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] argv) throws IOException, ParseException {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.example");
        UserService service = applicationContext.getBean(UserService.class);
        TraineeService service1 = applicationContext.getBean(TraineeService.class);
        TrainerService service2 = applicationContext.getBean(TrainerService.class);

        log.info(service.list().toString());
        log.warn(service1.list().toString());
        log.error(service2.list().toString());
    }
}
