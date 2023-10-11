package com.example.config;

import com.example.repo.*;
import com.example.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.example")
public class AppConfig {

    @Bean("UserRepository")
    public UserRepository getUserRepository() { return new UserRepository(); }

    @Bean("TraineeRepository")
    public TraineeRepository getTraineeRepository() {
        return new TraineeRepository();
    }

    @Bean("TrainerRepository")
    public TrainerRepository getTrainerRepository() {
        return new TrainerRepository();
    }

    @Bean("TrainingRepository")
    public TrainingRepository getTrainingRepository() {
        return new TrainingRepository();
    }

    @Bean("TrainingTypeRepository")
    public TrainingTypeRepository getTrainingTypeRepository() {
        return new TrainingTypeRepository();
    }

    @Bean("UserService")
    public UserService getUserService() {
        return new UserService(getUserRepository());
    }

    @Bean("TraineeService")
    public TraineeService getTraineeService() {
        return new TraineeService(getTraineeRepository());
    }

    @Bean("TrainerService")
    public TrainerService getTrainerService() { return new TrainerService(getTrainerRepository()); }

    @Bean("TrainingService")
    public TrainingService getTrainingService() { return new TrainingService(getTrainingRepository()); }

    @Bean("TrainingTypeService")
    public TrainingTypeService getTrainingTypeService() { return new TrainingTypeService(getTrainingTypeRepository()); }

}
