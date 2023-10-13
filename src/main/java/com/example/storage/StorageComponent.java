package com.example.storage;

import com.example.model.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class StorageComponent {

    private JSONParser parser = new JSONParser();
    private JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("src/main/resources/file.json"));
    private JSONArray array;

    public StorageComponent() throws IOException, ParseException {
    }

    public Map<Long, User> getUsersMap() {
        Map<Long, User> userStorage = new HashMap<>();
        array = (JSONArray) jsonObject.get("users");
        for (Object userObject : array) {
            JSONObject userJson = (JSONObject) userObject;
            long id = (Long) userJson.get("id");
            String firstName = (String) userJson.get("first-name");
            String lastName = (String) userJson.get("last-name");
            String username = (String) userJson.get("username");
            String password = (String) userJson.get("password");
            boolean isActive = (Boolean) userJson.get("is-active");

            User user = new User((int) id, firstName, lastName, username, password, isActive);
            userStorage.put(id, user);
        }

        return userStorage;
    }

    public Map<Long, Trainee> getTraineeMap() {
        Map<Long, Trainee> traineeStorage = new HashMap<>();
        array = (JSONArray) jsonObject.get("trainees");
        for (Object traineeObject : array) {
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

    public Map<Long, Trainer> getTrainerMap() {
        Map<Long, Trainer> trainerStorage = new HashMap<>();
        array = (JSONArray) jsonObject.get("trainers");
        for (Object trainerObject : array) {
            JSONObject trainerJson = (JSONObject) trainerObject;
            long id = (Long) trainerJson.get("id");
            long specializationId = (Long) trainerJson.get("specialization-id");
            long userId = (Long) trainerJson.get("user-id");

            Trainer trainer = new Trainer((int) id, (int) specializationId, (int) userId);
            trainerStorage.put(id, trainer);
        }

        return trainerStorage;
    }

    public Map<Long, Training> getTrainingMap() {
        Map<Long, Training> trainingStorage = new HashMap<>();
        array = (JSONArray) jsonObject.get("trainings");
        for (Object trainingObject : array) {
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

    public Map<Long, TrainingType> getTrainingTypeMap() {
        Map<Long, TrainingType> trainingTypeStorage = new HashMap<>();
        array = (JSONArray) jsonObject.get("training-types");
        for (Object trainingTypeObject : array) {
            JSONObject trainingTypeJson = (JSONObject) trainingTypeObject;
            long id = (Long) trainingTypeJson.get("id");
            String trainingTypeName = (String) trainingTypeJson.get("training-type-name");

            TrainingType trainingType = new TrainingType((int) id, trainingTypeName);
            trainingTypeStorage.put(id, trainingType);
        }

        return trainingTypeStorage;
    }
}
