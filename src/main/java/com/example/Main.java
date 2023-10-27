package com.example;

import com.example.model.Trainee;
import com.example.model.Trainer;
import com.example.model.Training;
import com.example.model.User;
import com.example.service.TraineeService;
import com.example.service.TrainerService;
import com.example.service.TrainingService;
import com.example.service.UserService;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] argv) throws IOException, ParseException {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.example");
        UserService service = applicationContext.getBean(UserService.class);
        TraineeService service1 = applicationContext.getBean(TraineeService.class);
        TrainerService service2 = applicationContext.getBean(TrainerService.class);
        TrainingService service3 = applicationContext.getBean(TrainingService.class);

        log.info(service.list().toString());
        log.warn(service1.list("John.Johnson", "WSaY9aVIHQ").toString());
        log.error(service2.list("John.Johnson", "WSaY9aVIHQ").toString());

        String username = "John.Johnson", password = "WSaY9aVIHQ";

        System.out.println();

//        System.out.println(service1.getTraineeByUsername(username, password));
//        System.out.println(service1.get(2, username, password));
//        System.out.println(service1.getTraineeTrainings(2, username,password));
//
//        System.out.println(service1.getTraineeByUsername(username, password));
//        service1.deactivateTrainee(2, username, password);
//        System.out.println(service.list());
//
//        User user = new User("John", "Johnson", true);
//        System.out.println(user);
//        System.out.println(service.create(user));
//        System.out.println(service.list());
//
//
//        System.out.println();
//
//        System.out.println(service1.create(new Trainee(new Date(2000, 11,11), "123 St.", 1)));
//        System.out.println(service1.get(1));
//        System.out.println(service1.update(new Trainee(new Date(1999, 11, 11), "111 St.", 1)));
//        System.out.println(service1.get(1));
//        service1.delete(1);
//        System.out.println(service1.get(1));
//
//        System.out.println();
//
//        System.out.println(service2.create(new Trainer(1, 1)));
//        System.out.println(service2.get(2));
//        System.out.println(service2.update(new Trainer(2, 2, 1)));
//        System.out.println(service2.get(2));
//
//        System.out.println();
//
//        System.out.println(service3.create(new Training(3, 1, 1, "swimming", 1, new Date(), 3)));
//        System.out.println(service3.get(3));

    }
}
