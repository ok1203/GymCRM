package com.example.repo;

import com.example.model.Training;
import com.example.storage.TrainingStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TrainingRepository {

    @Autowired
    private TrainingStorage storageComponent;

    public List<Training> findAll() {
        return storageComponent.getTrainingMap();
    }

    public Training create(Training training) {
        return storageComponent.createTraining(training);
    }

    public Optional<Training> get(int id) {
        return storageComponent.getTraining(id);
    }

    public void delete(int id) {
        storageComponent.deleteTraining(id);
    }
}
