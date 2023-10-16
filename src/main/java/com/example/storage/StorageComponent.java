package com.example.storage;

import com.example.annotation.Storage;
import com.example.model.*;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class StorageComponent {

    @Storage(entity = "users")
    private Map<Long, User> userStorage;
    @Storage(entity = "trainees")
    private Map<Long, Trainee> traineeStorage;
    @Storage(entity = "trainers")
    private Map<Long, Trainer> trainerStorage;
    @Storage(entity = "trainings")
    private Map<Long, Training> trainingStorage;
    @Storage(entity = "training-types")
    private Map<Long, TrainingType> trainingTypeStorage;

    public Map<Long, User> getUsersMap() {
        return userStorage;
    }

    public Map<Long, Trainee> getTraineeMap() {
        return traineeStorage;
    }

    public Map<Long, Trainer> getTrainerMap() {
        return trainerStorage;
    }

    public Map<Long, Training> getTrainingMap() {
        return trainingStorage;
    }

    public Map<Long, TrainingType> getTrainingTypeMap() {
        return trainingTypeStorage;
    }

    public Trainee createTrainee(Trainee trainee) {
        traineeStorage.put((long)trainee.getId(), trainee);
        return trainee;
    }

    public Trainer createTrainer(Trainer trainer) {
        trainerStorage.put((long)trainer.getId(), trainer);
        return trainer;
    }

    public Training createTraining(Training training) {
        trainingStorage.put((long)training.getId(), training);
        return training;
    }

    public Trainee getTrainee(int id) {
        return traineeStorage.get((long) id);
    }

    public Trainer getTrainer(int id) {
        return trainerStorage.get((long) id);
    }

    public Training getTraining(int id) {
        return trainingStorage.get((long) id);
    }

    public Trainee traineeUpdate(Trainee trainee) {
        if (traineeStorage.get(trainee.getId()) != null) {
            traineeStorage.replace((long)trainee.getId(), trainee);
        } else {
            return null;
        }

        return trainee;
    }

    public Trainer trainerUpdate(Trainer trainer) {
        if (trainerStorage.get(trainer.getId()) != null) {
            trainerStorage.replace((long)trainer.getId(), trainer);
        } else {
            return null;
        }

        return trainer;
    }

    public void deleteTrainee(int id) {
        traineeStorage.remove((long) id);
    }

}
