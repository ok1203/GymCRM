package com.example.repo;

import com.example.entity.Trainee;
import com.example.entity.Trainer;
import com.example.entity.Training;
import com.example.storage.TrainerStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TrainerRepository {

    @Autowired
    private TrainerStorage storageComponent;

    public List<Trainer> findAll(String username, String password) {
        return storageComponent.getTrainerMap(username, password);
    }

    public Trainer create(Trainer trainer) {
        return storageComponent.createTrainer(trainer);
    }

    public Optional<Trainer> get(int id, String username, String password) {
        return storageComponent.getTrainer(id, username, password);
    }

    public Trainer update(Trainer trainer, String username, String password) {
        return storageComponent.updateTrainer(trainer, username, password);
    }

    public Optional<Trainer> getTrainerByUsername(String username, String password) {
        return storageComponent.getTrainerByUsername(username, password);
    }

    public void changeTrainerPassword(int trainerId, String newPassword, String username, String password) {
        storageComponent.changeTrainerPassword(trainerId, newPassword, username, password);
    }

    public void deleteTrainer(int trainerId, String username, String password) {
        storageComponent.deleteTrainer(trainerId, username, password);
    }

    public void activateTrainer(int trainerId, String username, String password) {
        storageComponent.activateTrainer(trainerId, username, password);
    }

    public void deactivateTrainer(int trainerId, String username, String password) {
        storageComponent.deactivateTrainer(trainerId, username, password);
    }

    public List<Trainer> getNotAssignedActiveTrainersForTrainee(Trainee trainee, String username, String password) {
        return storageComponent.getNotAssignedActiveTrainersForTrainee(trainee, username, password);
    }

    public void addTrainingToTrainer(Trainer trainer, Training training, String username, String password) {
        storageComponent.addTrainingToTrainer(trainer, training, username, password);
    }

    public List<Training> getTrainerTrainings(int trainerId, String username, String password) {
        return storageComponent.getTrainerTrainings(trainerId, username, password);
    }
}
