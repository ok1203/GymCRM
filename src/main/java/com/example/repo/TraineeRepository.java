package com.example.repo;

import com.example.annotation.Storage;
import com.example.model.Trainee;
import com.example.storage.StorageComponent;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Map;

@Repository
public class TraineeRepository implements CrudRepository<Trainee> {

    @Storage
    StorageComponent storageComponent;

    public TraineeRepository() {
    }

    @Override
    public Map<Long, Trainee> findAll() throws IOException, ParseException {
        return storageComponent.getTraineeMap();
    }
}
