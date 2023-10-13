package com.example.repo;

import com.example.annotation.Storage;
import com.example.model.Training;
import com.example.storage.StorageComponent;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class TrainingRepository implements CrudRepository<Training>{

    @Storage
    StorageComponent storageComponent;

    public TrainingRepository() {
    }

    @Override
    public Map<Long, Training> findAll() {
        return storageComponent.getTrainingMap();
    }
}
