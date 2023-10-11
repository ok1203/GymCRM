package com.example.repo;

import com.example.model.Trainee;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository
public class TraineeRepository implements CrudRepository<Trainee> {

    @Override
    public Map<Long, Trainee> findAll() throws IOException, ParseException {
        Map<Long, Trainee> traineeStorage = null;
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("src/main/java/com/example/file.json"));

            JSONArray traineesArray = (JSONArray) jsonObject.get("trainees");

            traineeStorage = new HashMap<>();
            for (Object traineeObject : traineesArray) {
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

        } catch (Exception e) {
            e.printStackTrace();
        }
//        Trainee initExample = new Trainee(1,
//                new Date(2000, 11, 11),
//                "Backer St.",
//                1);
//        traineeStorage.put((long) initExample.getId(), initExample);
        return traineeStorage;
    }
}
