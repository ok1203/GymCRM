package com.example.repo;

import com.example.annotation.Storage;
import com.example.model.TrainingType;
import com.example.storage.StorageComponent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class TrainingTypeRepository implements CrudRepository<TrainingType> {

    Map<Long, TrainingType> trainingTypeStorage = null;

    @Storage
    StorageComponent storageComponent;

    public TrainingTypeRepository() throws IOException, ParseException {
    }

    @Override
    public Map<Long, TrainingType> findAll() {

        trainingTypeStorage = new HashMap<>();
        for (Object trainingTypeObject : storageComponent.getArray("training-types")) {
            JSONObject trainingTypeJson = (JSONObject) trainingTypeObject;
            long id = (Long) trainingTypeJson.get("id");
            String trainingTypeName = (String) trainingTypeJson.get("training-type-name");

            TrainingType trainingType = new TrainingType((int) id, trainingTypeName);
            trainingTypeStorage.put(id, trainingType);
        }

        return trainingTypeStorage;
    }
}
