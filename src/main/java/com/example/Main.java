package com.example;

import com.example.entity.Trainee;
import com.example.entity.Trainer;
import com.example.entity.Training;
import com.example.entity.User;
import com.example.service.TraineeService;
import com.example.service.TrainerService;
import com.example.service.TrainingService;
import com.example.service.UserService;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.Date;

@SpringBootApplication
@EnableDiscoveryClient
public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] argv) {

        ApplicationContext applicationContext = SpringApplication.run(Main.class, argv);

        UserService service = applicationContext.getBean(UserService.class);
        TraineeService service1 = applicationContext.getBean(TraineeService.class);
        TrainerService service2 = applicationContext.getBean(TrainerService.class);
        TrainingService service3 = applicationContext.getBean(TrainingService.class);

        log.info(service.list().toString());
        log.info(service1.list("John.Johnson2", "11111111").toString());
        log.info(service2.list("John.Johnson2", "11111111").toString());

        String username = "John.Johnson2", password = "11111111";

        //log.info(" ");

        //this block uses all userService methods

//        User user = new User("John", "Johnson", false);
//        log.info(service.create(user).toString());
//        log.info(service.get(1).toString());
//        log.info(service.list().toString());
//        service.delete(13);
//        log.info(service.list().toString());

        //log.info(" ");

        //this block uses save/create method of traineeService

//        log.info(service1.list(username, password).toString());
//        Trainee trainee = new Trainee(new Date(2000, 11,11), "123 St.", 15); // use unattached userId
//        log.info(service1.create(trainee).toString());
//        log.info(service1.list(username, password).toString());

        //log.info(" ");

        //this block uses get methods of traineeService

//        log.info(service1.get(12, username, password).toString());
//        log.info(service1.getTraineeByUsername(username, password).toString());

        //log.info(" ");

        // this block uses Training related operations of traineeService

//        log.info(service1.getTraineeTrainings(12, username, password).toString());
//        Training training = new Training(13, 4, "swimming", 1, new Date(2023, 11, 11), 2);
//        service1.addTrainingToTrainee(
//            service1.get(12, username, password).get(),
//            training,
//            username,
//            password);
//        log.info(service1.getTraineeTrainings(12, username, password).toString());

        //log.info(" ");

        //this block uses delete method of traineeService

//        log.info(service1.list(username, password).toString());
//        log.info(service.list().toString());
//        service1.delete(13, username, password);
//        log.info(service1.list(username, password).toString());
//        log.info(service.list().toString());

        //log.info(" ");

        //this block uses deleteByUsername method of traineeService

//        log.info(service1.list(username, password).toString());
//        log.info(service.list().toString());
//        log.info(service1.getTraineeTrainings(12, username, password).toString());
//        service1.deleteTraineeByUsername("John.Johnson2", "GU0is4y3lE");
//        log.info(service1.list(username, password).toString());
//        log.info(service.list().toString());
//        log.info(service1.getTraineeTrainings(12, username, password).toString());

        //log.info(" ");

        //this block uses update method of traineeService

//        Trainee traineeToUpdate = service1.get(11, username, password).get();
//        log.info(traineeToUpdate.toString());
//        traineeToUpdate.setAddress("111 ave.");
//        traineeToUpdate.setDateOfBirth(new Date(1900, 1, 1));
//        log.info(service1.update(traineeToUpdate,username,password).toString());
//        log.info(service1.get(11, username, password).get().toString());

        //log.info(" ");

        //this block uses changePassword method of traineeService

//        log.info(service.get(
//                service1.get(14, username, password).
//                        get().
//                        getUserId()).
//                toString());
//        service1.changeTraineePassword(14, "11111111", username, password);
//        log.info(service.get(
//                        service1.get(14, username, password).
//                                get().
//                                getUserId()).
//                toString());

        //log.info(" ");

        //this block uses activate/deactivate method of traineeService

//        log.info(service.get(
//                service1.get(14, username, password).
//                        get().
//                        getUserId()).
//                toString());
//        service1.activateTrainee(14, username, password);
//        log.info(service.get(
//                        service1.get(14, username, password).
//                                get().
//                                getUserId()).
//                toString());
//        service1.deactivateTrainee(14, username, password);
//        log.info(service.get(
//                        service1.get(14, username, password).
//                                get().
//                                getUserId()).
//                toString());

        //log.info(" ");

        //this block uses save/create method of trainerService

//        log.info(service2.list(username, password).toString());
//        Trainer trainer = new Trainer(1, 15); // use unattached userId
//        log.info(service2.create(trainer).toString());
//        log.info(service2.list(username, password).toString());

        //log.info(" ");

        //this block uses get methods of trainerService

//        log.info(service2.get(2, username, password).toString());
//        log.info(service2.getTrainerByUsername(username, password).toString());

        //log.info(" ");

        // this block uses Training related operations of trainerService

//        log.info(service2.getTrainerTrainings(2, username, password).toString());
//        Training training = new Training(14, 2, "swimming", 1, new Date(2023, 11, 11), 2);
//        service2.addTrainingToTrainer(
//            service2.get(2, username, password).get(),
//            training,
//            username,
//            password);
//        log.info(service2.getTrainerTrainings(2, username, password).toString());

        //log.info(" ");

        //this block uses delete method of trainerService

//        log.info(service2.list(username, password).toString());
//        service2.delete(2, username, password);
//        log.info(service2.list(username, password).toString());

        //log.info(" ");

        //this block uses update method of traineeService

//        Trainer trainerToUpdate = service2.get(5, username, password).get();
//        log.info(trainerToUpdate.toString());
//        trainerToUpdate.setSpecializationId(4);
//        log.info(service2.update(trainerToUpdate,username,password).toString());
//        log.info(service2.get(5, username, password).get().toString());

        //log.info(" ");

        //this block uses changePassword method of trainerService

//        log.info(service.get(
//                service2.get(5, username, password).
//                        get().
//                        getUserId()).
//                toString());
//        service2.changeTrainerPassword(5, "11111111", username, password);
//        log.info(service.get(
//                service2.get(5, username, password).
//                        get().
//                        getUserId()).
//                toString());
//
//        log.info(" ");

        //this block uses activate/deactivate method of trainerService

//        log.info(service.get(
//                service2.get(5, username, password).
//                        get().
//                        getUserId()).
//                toString());
//        service2.activateTrainer(5, username, password);
//        log.info(service.get(
//                service2.get(5, username, password).
//                        get().
//                        getUserId()).
//                toString());
//        service2.deactivateTrainer(5, username, password);
//        log.info(service.get(
//                service2.get(5, username, password).
//                        get().
//                        getUserId()).
//                toString());


//        log.info(service2.getNotAssignedActiveTrainersForTrainee("AAAAA.AAAAA").toString());
    }
}
