package com.example.service;

import com.example.exceptions.UnupdatableException;
import com.example.entity.Trainee;
import com.example.entity.Training;
import com.example.repo.*;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class TraineeService {

    private TraineeRepository repository;
    private UserRepository userRepository;
    private TrainingRepository trainingRepository;
    private TrainingTypeRepository trainingTypeRepository;

    @Autowired
    public TraineeService(TraineeRepository traineeRepositoryб,
                          UserRepository userRepository,
                          TraineeRepository traineeRepository,
                          TrainingRepository trainingRepository,
                          TrainingTypeRepository trainingTypeRepository) {
        repository = traineeRepository;
        this.userRepository = userRepository;
        this.trainingRepository = trainingRepository;
        this.trainingTypeRepository = trainingTypeRepository;
    }

    public List<Trainee> list(String username, String password) throws IOException, ParseException {
        return repository.findAll(username, password);
    }

    public Trainee create(Trainee trainee) {
        return repository.create(trainee);
    }

    public Optional<Trainee> get(int id, String username, String password) throws IOException, ParseException {
        return repository.get(id, username, password);
    }

    @Transactional
    public void delete(int id, String username, String password) throws IOException, ParseException {
        for (Training training : repository.getTraineeTrainings(id, username, password)) {
            trainingTypeRepository.delete(training.getTrainingTypeId());
            trainingRepository.delete(training.getId());
        }
        userRepository.delete(repository.get(id, username, password).get().getUserId());
        repository.delete(id, username, password);
    }

    public Trainee update(Trainee trainee, String username, String password) throws IOException, ParseException, UnupdatableException {
        if (get(trainee.getId(), username, password).isEmpty()) {
            throw new UnupdatableException("trainee is null");
        }
        return repository.update(trainee, username, password);
    }

    public Optional<Trainee> getTraineeByUsername(String username, String password) {
        return repository.getTraineeByUsername(username, password);
    }

    public void changeTraineePassword(int traineeId, String newPassword, String username, String password) {
        userRepository.changeUserPassword(repository.get(traineeId, username, password).get().getUserId(), newPassword);
    }

    @Transactional
    public void activateTrainee(int traineeId, String username, String password) {
        userRepository.activateUser(repository.get(traineeId, username, password).get().getUserId());
    }

    @Transactional
    public void deactivateTrainee(int traineeId, String username, String password) {
        userRepository.deactivateUser(repository.get(traineeId, username, password).get().getUserId());
    }

    @Transactional
    public void deleteTraineeByUsername(String username, String password) {
        int id = repository.getTraineeByUsername(username, password).get().getId();
        for (Training training : repository.getTraineeTrainings(id, username, password)) {
            trainingTypeRepository.delete(training.getTrainingTypeId());
            trainingRepository.delete(training.getId());
        }
        userRepository.delete(repository.get(id, username, password).get().getUserId());
        repository.delete(id, username, password);
    }

    public void addTrainingToTrainee(Trainee trainee, Training training, String username, String password) {
        repository.addTrainingToTrainee(trainee, training, username, password);
    }

    public List<Training> getTraineeTrainings(int traineeId, String username, String password) {
        return repository.getTraineeTrainings(traineeId, username, password);
    }
}
