package com.example.repo;

import com.example.annotation.Storage;
import com.example.model.Trainee;
import com.example.storage.StorageComponent;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository
public class TraineeRepository implements CrudRepository<Trainee> {

    Map<Long, Trainee> traineeStorage = null;

    @Storage
    StorageComponent storageComponent;

    public TraineeRepository() throws IOException, ParseException {
    }

    @Override
    public Map<Long, Trainee> findAll() throws IOException, ParseException {

        traineeStorage = new HashMap<>();
        for (Object traineeObject : storageComponent.getArray("trainees")) {
            JSONObject traineeJson = (JSONObject) traineeObject;
            long id = (Long) traineeJson.get("id");
            JSONObject dateOfBirthJson = (JSONObject) traineeJson.get("date-of-birth");
            int year = (int) (long) dateOfBirthJson.get("year");
            int month = (int) (long) dateOfBirthJson.get("month");
            int day = (int) (long) dateOfBirthJson.get("day");
            //System.out.println(year + " " + month + " " + day);
            String address = (String) traineeJson.get("address");
            long userId = (Long) traineeJson.get("user-id");

            Trainee trainee = new Trainee((int) id, new Date(year, month, day), address, (int) userId);
            traineeStorage.put(id, trainee);
        }

        return traineeStorage;
    }
}
