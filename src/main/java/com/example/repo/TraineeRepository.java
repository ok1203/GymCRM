package com.example.repo;

import com.example.annotation.Storage;
import com.example.model.Trainee;
import com.example.storage.StorageComponent;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Map;

@Repository
public class TraineeRepository implements CrudRepository<Trainee> {

    @Autowired
    private StorageComponent storageComponent;

    @Override
    public Map<Long, Trainee> findAll() throws IOException, ParseException {
        return storageComponent.getTraineeMap();
    }

    public Trainee create(Trainee trainee) {
        return storageComponent.createTrainee(trainee);
    }

    public Trainee get(int id) {
        return storageComponent.getTrainee(id);
    }

    public Trainee update(Trainee trainee){
        return storageComponent.traineeUpdate(trainee);
    }

    public void delete(int id) {
        storageComponent.deleteTrainee(id);
    }

}
