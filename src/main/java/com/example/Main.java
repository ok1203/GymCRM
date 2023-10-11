package com.example;

import com.example.config.AppConfig;
import com.example.service.TraineeService;
import com.example.service.TrainerService;
import com.example.service.UserService;
import org.json.simple.parser.ParseException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class Main {
    public static void main(String[] argv) throws IOException, ParseException {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        UserService service = applicationContext.getBean("UserService", UserService.class);
        TraineeService service1 = applicationContext.getBean("TraineeService", TraineeService.class);
        TrainerService service2 = applicationContext.getBean("TrainerService", TrainerService.class);

        System.out.println(service.list());
        System.out.println(service1.list());
        System.out.println(service2.list());
    }
}
