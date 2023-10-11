package com.example.repo;

import com.example.model.TrainingType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

@Repository
public class TrainingTypeRepository implements CrudRepository<TrainingType> {

    @Override
    public Map<Long, TrainingType> findAll() {
        Map<Long, TrainingType> trainingTypeStorage = null;
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("src/main/java/com/example/file.json"));

            JSONArray trainingTypesArray = (JSONArray) jsonObject.get("training-types");

            trainingTypeStorage = new HashMap<>();
            for (Object trainingTypeObject : trainingTypesArray) {
                JSONObject trainingTypeJson = (JSONObject) trainingTypeObject;
                long id = (Long) trainingTypeJson.get("id");
                String trainingTypeName = (String) trainingTypeJson.get("training-type-name");

                TrainingType trainingType = new TrainingType((int) id, trainingTypeName);
                trainingTypeStorage.put(id, trainingType);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

//        Map<Long, TrainingType> trainingTypeStorage = new HashMap<>();
//        TrainingType initExample = new TrainingType(1, "Swimming");
//        trainingTypeStorage.put((long) initExample.getId(), initExample);
        return trainingTypeStorage;
    }
}
