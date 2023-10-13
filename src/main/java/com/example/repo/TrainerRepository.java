package com.example.repo;

import com.example.annotation.Storage;
import com.example.model.Trainer;
import com.example.storage.StorageComponent;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class TrainerRepository implements CrudRepository<Trainer> {

    @Storage
    StorageComponent storageComponent;

    public TrainerRepository() {
    }

    @Override
    public Map<Long, Trainer> findAll() {
        return storageComponent.getTrainerMap();
    }
}
