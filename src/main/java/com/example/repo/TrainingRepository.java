package com.example.repo;

import com.example.model.Training;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository
public class TrainingRepository implements CrudRepository<Training>{

    @Override
    public Map<Long, Training> findAll() {
        Map<Long, Training> trainingStorage = null;
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("src/main/java/com/example/file.json"));

            JSONArray trainingsArray = (JSONArray) jsonObject.get("trainings");

            trainingStorage = new HashMap<>();
            for (Object trainingObject : trainingsArray) {
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

        } catch (Exception e) {
            e.printStackTrace();
        }

//        Map<Long, Training> trainingStorage = new HashMap<>();
//        Training initExample = new Training(1,
//                1,
//                1,
//                "Swimming",
//                1,
//                new Date(),
//                1);
//        trainingStorage.put((long) initExample.getId(), initExample);
        return trainingStorage;
    }
}
