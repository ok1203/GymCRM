package com.example.repo;

import com.example.annotation.Storage;
import com.example.model.TrainingType;
import com.example.storage.StorageComponent;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class TrainingTypeRepository implements CrudRepository<TrainingType> {

    @Storage
    StorageComponent storageComponent;

    public TrainingTypeRepository() {
    }

    @Override
    public Map<Long, TrainingType> findAll() {
        return storageComponent.getTrainingTypeMap();
    }
}
