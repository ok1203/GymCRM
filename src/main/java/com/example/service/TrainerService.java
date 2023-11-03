package com.example.service;

import com.example.exceptions.UnupdatableException;
import com.example.entity.Trainee;
import com.example.entity.Trainer;
import com.example.entity.Training;
import com.example.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TrainerService {

    private TrainerRepository repository;
    private UserRepository userRepository;
    private TrainingRepository trainingRepository;
    private TrainingTypeRepository trainingTypeRepository;

    @Autowired
    public TrainerService(TrainerRepository trainerRepository,
                          UserRepository userRepository,
                          TrainingRepository trainingRepository,
                          TrainingTypeRepository trainingTypeRepository) {
        repository = trainerRepository;
        this.userRepository = userRepository;
        this.trainingRepository = trainingRepository;
        this.trainingTypeRepository = trainingTypeRepository;
    }

    @Transactional(readOnly = true)
    public List<Trainer> list(String username, String password) {
        return repository.findAll(username, password);
    }

    @Transactional
    public Trainer create(Trainer trainer) {
        return repository.create(trainer);
    }

    @Transactional(readOnly = true)
    public Optional<Trainer> get(int id, String username, String password) {
        return repository.get(id, username, password);
    }

    @Transactional
    public Trainer update(Trainer trainer, String username, String password) throws UnupdatableException {
        if (get(trainer.getId(), username, password).isEmpty()) {
            throw new UnupdatableException("Trainer is null");
        }
        return repository.update(trainer, username, password);
    }

    @Transactional(readOnly = true)
    public Optional<Trainer> getTrainerByUsername(String username, String password) {
        return repository.getTrainerByUsername(username, password);
    }

    @Transactional
    public void changeTrainerPassword(int trainerId, String newPassword, String username, String password) {
        userRepository.changeUserPassword(repository.get(trainerId, username, password).get().getUserId(), newPassword);
    }

    @Transactional
    public void delete(int trainerId, String username, String password) {
        for (Training training : repository.getTrainerTrainings(trainerId, username, password)) {
            trainingTypeRepository.delete(training.getTrainingTypeId());
            trainingRepository.delete(training.getId());
        }
        repository.deleteTrainer(trainerId, username, password);
    }

    @Transactional
    public void activateTrainer(int trainerId, String username, String password) {
        userRepository.activateUser(repository.get(trainerId, username, password).get().getUserId());
    }

    @Transactional
    public void deactivateTrainer(int trainerId, String username, String password) {
        userRepository.deactivateUser(repository.get(trainerId, username, password).get().getUserId());
    }

    @Transactional(readOnly = true)
    public List<Trainer> getNotAssignedActiveTrainersForTrainee(Trainee trainee, String username, String password) {
        return repository.getNotAssignedActiveTrainersForTrainee(trainee, username, password);
    }

    @Transactional
    public void addTrainingToTrainer(Trainer trainer, Training training, String username, String password) {
        training.setTrainerId(trainer.getId());
        trainingRepository.create(training);
    }

    @Transactional(readOnly = true)
    public List<Training> getTrainerTrainings(int trainerId, String username, String password) {
        return repository.getTrainerTrainings(trainerId, username, password);
    }
}
