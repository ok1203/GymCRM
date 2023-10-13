package com.example.repo;

import com.example.annotation.Storage;
import com.example.model.Trainer;
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
public class TrainerRepository implements CrudRepository<Trainer> {

    Map<Long, Trainer> trainerStorage = null;

    @Storage
    StorageComponent storageComponent;

    public TrainerRepository() throws IOException, ParseException {
    }

    @Override
    public Map<Long, Trainer> findAll() {

        trainerStorage = new HashMap<>();
        for (Object trainerObject : storageComponent.getArray("trainers")) {
            JSONObject trainerJson = (JSONObject) trainerObject;
            long id = (Long) trainerJson.get("id");
            long specializationId = (Long) trainerJson.get("specialization-id");
            long userId = (Long) trainerJson.get("user-id");

            Trainer trainer = new Trainer((int) id, (int) specializationId, (int) userId);
            trainerStorage.put(id, trainer);
        }

        return trainerStorage;
    }
}
