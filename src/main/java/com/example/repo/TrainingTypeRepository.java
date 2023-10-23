package com.example.repo;

import com.example.model.TrainingType;
import com.example.storage.StorageComponent;
import com.example.storage.TrainingTypeStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class TrainingTypeRepository implements CrudRepository<TrainingType> {

    @Autowired
    private TrainingTypeStorage storageComponent;

    @Override
    public Map<Integer, TrainingType> findAll() {
        return storageComponent.getTrainingTypeMap();
    }
}
