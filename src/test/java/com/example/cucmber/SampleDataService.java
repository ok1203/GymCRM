package com.example.cucmber;

import com.example.entity.*;
import com.example.repo.*;
import com.example.rest.request.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class SampleDataService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    TraineeRepository traineeRepository;
    @Autowired
    TrainerRepository trainerRepository;
    @Autowired
    TrainingRepository trainingRepository;
    @Autowired
    TrainingTypeRepository trainingTypeRepository;

    private TraineeRegistrationRequest traineeRegistrationRequest;
    private TrainerRegistrationRequest trainerRegistrationRequest;
    private TraineeUpdateRequest traineeUpdateRequest;
    private TrainerUpdateRequest trainerUpdateRequest;
    private TrainingRequest trainingRequest;

    @Transactional
    public void givenTrainee(String name, String lastName, String address, Date date) {
        User user = new User(name, lastName, true);
        userRepository.create(user);

        Trainee trainee = new Trainee();
        trainee.setDateOfBirth(date);
        trainee.setAddress(address);
        trainee.setUserId(user.getId());
        traineeRepository.create(trainee);
    }

    @Transactional
    public void givenTrainer(String name, String lastName, int specializationId) {
        User user = new User(name, lastName, true);
        userRepository.create(user);

        Trainer trainer = new Trainer();
        trainer.setSpecializationId(specializationId);
        trainer.setUserId(user.getId());
        trainerRepository.create(trainer);
    }

    @Transactional
    public void givenTraining(String trainerName, String traineeName, Date date, int duration, String name) {
        Training training = new Training();
        Trainer trainer = trainerRepository.getTrainerByUsername(trainerName).orElseThrow(() -> new RuntimeException("Trainer not found"));
        training.setTrainerId(trainer.getId());
        Trainee trainee = traineeRepository.getTraineeByUsername(traineeName).orElseThrow(() -> new RuntimeException("Trainee not found"));
        training.setTraineeId(trainee.getId());
        training.setDate(date);
        training.setDuration(duration);
        training.setName(name);
        TrainingType trainingType = trainingTypeRepository.getByName(name).orElse(null);
        if (trainingType == null) {
            trainingType = new TrainingType(name);
            trainingTypeRepository.create(trainingType);
        }
        training.setTrainingTypeId(trainingTypeRepository.getByName(name).get().getId());
    }

    public TraineeRegistrationRequest getTraineeRegistrationRequest() {
        traineeRegistrationRequest = new TraineeRegistrationRequest();
        traineeRegistrationRequest.setFirstName("Test");
        traineeRegistrationRequest.setLastName("Trainee");
        traineeRegistrationRequest.setAddress("Test st. Test");
        traineeRegistrationRequest.setDateOfBirth(new Date());
        return traineeRegistrationRequest;
    }

    public TrainerRegistrationRequest getTrainerRegistrationRequest() {
        trainerRegistrationRequest = new TrainerRegistrationRequest();
        trainerRegistrationRequest.setFirstName("Test");
        trainerRegistrationRequest.setLastName("Trainer");
        trainerRegistrationRequest.setSpecializationId(1);
        return trainerRegistrationRequest;
    }

    public TraineeUpdateRequest getTraineeUpdateRequest() {
        traineeUpdateRequest = new TraineeUpdateRequest();
        traineeUpdateRequest.setFirstName("Update");
        traineeUpdateRequest.setLastName("Test");
        traineeUpdateRequest.setUsername("Valid-Test.UpdateTrainee");
        traineeUpdateRequest.setAddress("Update Test st. Test");
        traineeUpdateRequest.setDateOfBirth(new Date());
        traineeUpdateRequest.setActive(false);
        return traineeUpdateRequest;
    }

    public TrainerUpdateRequest getTrainerUpdateRequest() {
        trainerUpdateRequest = new TrainerUpdateRequest();
        trainerUpdateRequest.setFirstName("Update");
        trainerUpdateRequest.setLastName("Test");
        trainerUpdateRequest.setUsername("Valid-Test.UpdateTrainer");
        trainerUpdateRequest.setSpecializationId(3);
        trainerUpdateRequest.setActive(false);
        return trainerUpdateRequest;
    }

    public TrainingRequest getTrainingRequest() {
        trainingRequest = new TrainingRequest();
        trainingRequest.setTrainingName("Swimming");
        trainingRequest.setTraineeName("Valid-Test.TrainingTrainee");
        trainingRequest.setTrainerName("Valid-Test.TrainingTrainer");
        trainingRequest.setDate(new Date());
        trainingRequest.setDuration(60);
        return trainingRequest;
    }
}
