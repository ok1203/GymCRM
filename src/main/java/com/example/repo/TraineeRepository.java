package com.example.repo;

import com.example.entity.Trainee;
import com.example.entity.Training;
import com.example.storage.TraineeStorage;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Repository
public class TraineeRepository {

    @Autowired
    private TraineeStorage storageComponent;

    public List<Trainee> findAll(String username, String password) throws IOException, ParseException {
        return storageComponent.getTraineeMap(username, password);
    }

    public Trainee create(Trainee trainee) {
        return storageComponent.createTrainee(trainee);
    }

    public Optional<Trainee> get(int id, String username, String password) {
        return storageComponent.getTrainee(id, username, password);
    }

    public Trainee update(Trainee trainee, String username, String password){
        return storageComponent.updateTrainee(trainee, username, password);
    }

    public void delete(int id, String username, String password) {
        storageComponent.deleteTrainee(id, username, password);
    }

    public Optional<Trainee> getTraineeByUsername(String username, String password) {
        return storageComponent.getTraineeByUsername(username, password);
    }

    public void changeTraineePassword(int traineeId, String newPassword, String username, String password) {
        storageComponent.changeTraineePassword(traineeId, newPassword, username, password);
    }

    public void activateTrainee(int traineeId, String username, String password) {
        storageComponent.activateTrainee(traineeId, username, password);
    }

    public void deactivateTrainee(int traineeId, String username, String password) {
        storageComponent.deactivateTrainee(traineeId, username, password);
    }

    public void deleteTraineeByUsername(String username, String password) {
        storageComponent.deleteTraineeByUsername(username, password);
    }

    public void addTrainingToTrainee(Trainee trainee, Training training, String username, String password) {
        storageComponent.addTrainingToTrainee(trainee, training, username, password);
    }

    public List<Training> getTraineeTrainings(int traineeId, String username, String password) {
        return storageComponent.getTraineeTrainings(traineeId, username, password);
    }
}
