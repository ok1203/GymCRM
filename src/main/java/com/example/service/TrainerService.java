package com.example.service;

import com.example.exceptions.UnupdatableException;
import com.example.entity.Trainee;
import com.example.entity.Trainer;
import com.example.entity.Training;
import com.example.repo.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainerService {

    private TrainerRepository repository;

    @Autowired
    public TrainerService(TrainerRepository trainerRepository) {
        repository = trainerRepository;
    }

    public List<Trainer> list(String username, String password) {
        return repository.findAll(username, password);
    }

    public Trainer create(Trainer trainer) {
        return repository.create(trainer);
    }

    public Optional<Trainer> get(int id, String username, String password) {
        return repository.get(id, username, password);
    }

    public Trainer update(Trainer trainer, String username, String password) throws UnupdatableException {
        if (get(trainer.getId(), username, password).isEmpty()) {
            throw new UnupdatableException("Trainer is null");
        }
        return repository.update(trainer, username, password);
    }

    public Optional<Trainer> getTrainerByUsername(String username, String password) {
        return repository.getTrainerByUsername(username, password);
    }

    public void changeTrainerPassword(int trainerId, String newPassword, String username, String password) {
        repository.changeTrainerPassword(trainerId, newPassword, username, password);
    }

    public void deleteTrainer(int trainerId, String username, String password) {
        repository.deleteTrainer(trainerId, username, password);
    }

    public void activateTrainer(int trainerId, String username, String password) {
        repository.activateTrainer(trainerId, username, password);
    }

    public void deactivateTrainer(int trainerId, String username, String password) {
        repository.deactivateTrainer(trainerId, username, password);
    }

    public List<Trainer> getNotAssignedActiveTrainersForTrainee(Trainee trainee, String username, String password) {
        return repository.getNotAssignedActiveTrainersForTrainee(trainee, username, password);
    }

    public void addTrainingToTrainer(Trainer trainer, Training training, String username, String password) {
        repository.addTrainingToTrainer(trainer, training, username, password);
    }

    public List<Training> getTrainerTrainings(int trainerId, String username, String password) {
        return repository.getTrainerTrainings(trainerId, username, password);
    }
}
