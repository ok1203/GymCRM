package com.example.repo;

import com.example.annotation.Storage;
import com.example.model.TrainingType;
import com.example.storage.StorageComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class TrainingTypeRepository implements CrudRepository<TrainingType> {

    @Autowired
    private StorageComponent storageComponent;

    @Override
    public Map<Long, TrainingType> findAll() {
        return storageComponent.getTrainingTypeMap();
    }
}
