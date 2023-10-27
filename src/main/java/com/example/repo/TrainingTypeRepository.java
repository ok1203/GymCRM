package com.example.repo;

import com.example.entity.TrainingType;
import com.example.storage.TrainingTypeStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TrainingTypeRepository {

    @Autowired
    private TrainingTypeStorage storageComponent;

    public List<TrainingType> findAll() {
        return storageComponent.getTrainingTypeMap();
    }

    public void delete(int id) {
        storageComponent.deleteTrainingType(id);
    }
}
