package com.example.service;

import com.example.model.Trainee;
import com.example.model.Training;
import com.example.repo.TraineeRepository;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class TraineeService {

    private TraineeRepository repository;

    @Autowired
    public TraineeService(TraineeRepository traineeRepository) {
        repository = traineeRepository;
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

    public void delete(int id, String username, String password) throws IOException, ParseException {
        repository.delete(id, username, password);
    }

    public Trainee update(Trainee trainee, String username, String password) throws IOException, ParseException {
        if (get(trainee.getId(), username, password).isEmpty()) {
            throw new NullPointerException("trainee is null");
        }
        return repository.update(trainee, username, password);
    }

    public Optional<Trainee> getTraineeByUsername(String username, String password) {
        return repository.getTraineeByUsername(username, password);
    }

    public void changeTraineePassword(int traineeId, String newPassword, String username, String password) {
        repository.changeTraineePassword(traineeId, newPassword, username, password);
    }

    public void activateTrainee(int traineeId, String username, String password) {
        repository.activateTrainee(traineeId, username, password);
    }

    public void deactivateTrainee(int traineeId, String username, String password) {
        repository.deactivateTrainee(traineeId, username, password);
    }

    public void deleteTraineeByUsername(String username, String password) {
        repository.deleteTraineeByUsername(username, password);
    }

    public void addTrainingToTrainee(Trainee trainee, Training training, String username, String password) {
        repository.addTrainingToTrainee(trainee, training, username, password);
    }

    public List<Training> getTraineeTrainings(int traineeId, String username, String password) {
        return repository.getTraineeTrainings(traineeId, username, password);
    }
}
