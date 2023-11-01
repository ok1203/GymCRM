package com.example;

import com.example.entity.Trainee;
import com.example.entity.User;
import com.example.service.TraineeService;
import com.example.service.TrainerService;
import com.example.service.TrainingService;
import com.example.service.UserService;
import org.hibernate.SessionFactory;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.Date;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] argv) throws IOException, ParseException {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.example");
        UserService service = applicationContext.getBean(UserService.class);
        TraineeService service1 = applicationContext.getBean(TraineeService.class);
        TrainerService service2 = applicationContext.getBean(TrainerService.class);
        //TrainingService service3 = applicationContext.getBean(TrainingService.class);

        log.info(service.list().toString());
        log.warn(service1.list("John.Johnson", "WSaY9aVIHQ").toString());
        log.error(service2.list("John.Johnson", "WSaY9aVIHQ").toString());

        String username = "John.Johnson", password = "WSaY9aVIHQ";

        log.info(" ");

        //this block uses all userService methods

//        User user = new User("John", "Johnson", false);
//        log.info(service.create(user).toString());
//        log.info(service.get(1).toString());
//        log.info(service.list().toString());
//        service.delete(11);
//        log.info(service.list().toString());

        log.info(" ");

        //this block uses save/create method of traineeService

//        log.info(service1.list(username, password).toString());
//        Trainee trainee = new Trainee(new Date(2000, 11,11), "123 St.", 1); // use unattached userId
//        log.info(service1.create(trainee).toString());
//        log.info(service1.list(username, password).toString());


        log.info(" ");

        //this block uses delete method of traineeService

//        log.info(service1.list(username, password).toString());
//        log.info(service.list().toString());
//        service1.delete(1, username, password);
//        log.info(service1.list(username, password).toString());
//        log.info(service.list().toString());


        log.info(" ");

        //this block uses update method of traineeService

        Trainee traineeToUpdate = service1.get(11, username, password).get();
        log.info(traineeToUpdate.toString());
        traineeToUpdate.setAddress("111 ave.");
        traineeToUpdate.setDateOfBirth(new Date(1900, 1, 1));
        log.info(service1.update(traineeToUpdate,username,password).toString());
        log.info(service1.get(11, username, password).get().toString());

    }
}
