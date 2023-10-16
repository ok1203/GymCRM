package com.example.repo;

import com.example.annotation.Storage;
import com.example.model.Training;
import com.example.storage.StorageComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class TrainingRepository implements CrudRepository<Training>{

    @Autowired
    private StorageComponent storageComponent;

    @Override
    public Map<Long, Training> findAll() {
        return storageComponent.getTrainingMap();
    }

    public Training create(Training training) {
        return storageComponent.createTraining(training);
    }

    public Training get(int id) {
        return storageComponent.getTraining(id);
    }
}
