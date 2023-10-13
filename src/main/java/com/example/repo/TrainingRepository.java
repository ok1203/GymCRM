package com.example.repo;

import com.example.annotation.Storage;
import com.example.model.Training;
import com.example.storage.StorageComponent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository
public class TrainingRepository implements CrudRepository<Training>{

    Map<Long, Training> trainingStorage = null;

    @Storage
    StorageComponent storageComponent;

    public TrainingRepository() throws IOException, ParseException {
    }

    @Override
    public Map<Long, Training> findAll() {

        trainingStorage = new HashMap<>();
        for (Object trainingObject : storageComponent.getArray("trainings")) {
            JSONObject trainingJson = (JSONObject) trainingObject;
            long id = (Long) trainingJson.get("id");
            long traineeId = (Long) trainingJson.get("trainee-id");
            long trainerId = (Long) trainingJson.get("trainer-id");
            String trainingName = (String) trainingJson.get("training-name");
            long trainingTypeId = (Long) trainingJson.get("training-type-id");
            JSONObject trainingDateJson = (JSONObject) trainingJson.get("training-date");
            int year = (int) (long) trainingDateJson.get("year");
            int month = (int) (long) trainingDateJson.get("month");
            int day = (int) (long) trainingDateJson.get("day");
            int trainingDuration = (int) (long) trainingJson.get("training-duration");

            Training training = new Training((int) id, (int) traineeId, (int) trainerId, trainingName, (int) trainingTypeId, new Date(year, month, day), trainingDuration);
            trainingStorage.put(id, training);
        }

        return trainingStorage;
    }
}
