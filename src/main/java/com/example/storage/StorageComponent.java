package com.example.storage;

import com.example.annotation.Storage;
import com.example.model.*;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class StorageComponent {

    @Storage(entity = "users")
    private Map<Integer, User> userStorage;
    @Storage(entity = "trainees")
    private Map<Integer, Trainee> traineeStorage;
    @Storage(entity = "trainers")
    private Map<Integer, Trainer> trainerStorage;
    @Storage(entity = "trainings")
    private Map<Integer, Training> trainingStorage;
    @Storage(entity = "training-types")
    private Map<Integer, TrainingType> trainingTypeStorage;

    public Map<Integer, User> getUsersMap() {
        return userStorage;
    }

    public Map<Integer, Trainee> getTraineeMap() {
        return traineeStorage;
    }

    public Map<Integer, Trainer> getTrainerMap() {
        return trainerStorage;
    }

    public Map<Integer, Training> getTrainingMap() {
        return trainingStorage;
    }

    public Map<Integer, TrainingType> getTrainingTypeMap() {
        return trainingTypeStorage;
    }

    public Trainee createTrainee(Trainee trainee) {
        traineeStorage.put(trainee.getId(), trainee);
        return trainee;
    }

    public Trainer createTrainer(Trainer trainer) {
        trainerStorage.put(trainer.getId(), trainer);
        return trainer;
    }

    public Training createTraining(Training training) {
        trainingStorage.put(training.getId(), training);
        return training;
    }

    public Optional<Trainee> getTrainee(int id) {
        return Optional.ofNullable(traineeStorage.get(id));
    }

    public Optional<Trainer> getTrainer(int id) {
        return Optional.ofNullable(trainerStorage.get(id));
    }

    public Optional<Training> getTraining(int id) {
        return Optional.ofNullable(trainingStorage.get(id));
    }

    public Trainee traineeUpdate(Trainee trainee) {
        if (!traineeStorage.containsKey(trainee.getId())) {
            throw new NullPointerException("Does not contain such object");
        }
        traineeStorage.replace(trainee.getId(), trainee);
        return trainee;
    }

    public Trainer trainerUpdate(Trainer trainer) {
        if (!trainerStorage.containsKey(trainer.getId())) {
            throw new NullPointerException("Does not contain such object");
        }
        trainerStorage.replace(trainer.getId(), trainer);
        return trainer;
    }

    public void deleteTrainee(int id) {
        traineeStorage.remove(id);
    }

}
