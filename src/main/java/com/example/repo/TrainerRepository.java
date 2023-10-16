package com.example.repo;

import com.example.annotation.Storage;
import com.example.model.Trainer;
import com.example.storage.StorageComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class TrainerRepository implements CrudRepository<Trainer> {

    @Autowired
    private StorageComponent storageComponent;

    @Override
    public Map<Long, Trainer> findAll() {
        return storageComponent.getTrainerMap();
    }

    public Trainer create(Trainer trainer) {
        storageComponent.createTrainer(trainer);
        return trainer;
    }

    public Trainer get(int id) {
        return storageComponent.getTrainer(id);
    }

    public Trainer update(Trainer trainer) {
        return storageComponent.trainerUpdate(trainer);
    }
}
