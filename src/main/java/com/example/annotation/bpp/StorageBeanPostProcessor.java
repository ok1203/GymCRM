package com.example.annotation.bpp;

import com.example.annotation.Storage;
import com.example.model.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class StorageBeanPostProcessor implements BeanPostProcessor {
    private static final Logger log = LoggerFactory.getLogger(StorageBeanPostProcessor.class);
    private static JSONParser parser = new JSONParser();;
    private static JSONObject jsonObject;
    private static JSONArray array;
    private Map<Long, User> userStorage = new HashMap<>();
    private Map<Long, Trainee> traineeStorage = new HashMap<>();
    private Map<Long, Trainer> trainerStorage = new HashMap<>();
    private Map<Long, Training> trainingStorage = new HashMap<>();
    private Map<Long, TrainingType> trainingTypeStorage = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        log.debug("Starting storage injection for bean: " + beanName);

        Class<?> beanClass = bean.getClass();
        Arrays.stream(beanClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Storage.class))
                .forEach(field -> {
                    field.setAccessible(true);
                    Storage annotation = field.getAnnotation(Storage.class);
                    array = (JSONArray) jsonObject.get(annotation.entity());

                    switch (annotation.entity()) {
                        case "users":
                            ReflectionUtils.setField(field, bean, userStorage);
                            break;
                        case "trainees":
                            ReflectionUtils.setField(field, bean, traineeStorage);
                            break;
                        case "trainers":
                            ReflectionUtils.setField(field, bean, trainerStorage);
                            break;
                        case "trainings":
                            ReflectionUtils.setField(field, bean, trainingStorage);
                            break;
                        case "training-types":
                            ReflectionUtils.setField(field, bean, trainingTypeStorage);
                            break;
                    }

                });

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    public StorageBeanPostProcessor() {
        if (jsonObject == null) {
            log.debug("Loading JSON file...");
            try {
                jsonObject = (JSONObject) parser.parse(new FileReader("src/main/resources/file.json"));
                log.info("JSON file loaded successfully.");
            } catch (IOException | ParseException e) {
                log.error("Error loading or parsing the JSON file: " + e.getMessage());
            }
        }

        array = (JSONArray) this.jsonObject.get("users");
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

        array = (JSONArray) this.jsonObject.get("trainees");
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

        array = (JSONArray) this.jsonObject.get("trainers");
        for (Object trainerObject : array) {
            JSONObject trainerJson = (JSONObject) trainerObject;
            long id = (Long) trainerJson.get("id");
            long specializationId = (Long) trainerJson.get("specialization-id");
            long userId = (Long) trainerJson.get("user-id");

            Trainer trainer = new Trainer((int) id, (int) specializationId, (int) userId);
            trainerStorage.put(id, trainer);
        }

        array = (JSONArray) this.jsonObject.get("trainings");
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

        array = (JSONArray) this.jsonObject.get("training-types");
        for (Object trainingTypeObject : array) {
            JSONObject trainingTypeJson = (JSONObject) trainingTypeObject;
            long id = (Long) trainingTypeJson.get("id");
            String trainingTypeName = (String) trainingTypeJson.get("training-type-name");

            TrainingType trainingType = new TrainingType((int) id, trainingTypeName);
            trainingTypeStorage.put(id, trainingType);
        }
    }
}
