package com.example.repo;

import com.example.model.Trainer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

@Repository
public class TrainerRepository implements CrudRepository<Trainer> {

    @Override
    public Map<Long, Trainer> findAll() {
        Map<Long, Trainer> trainerStorage = null;
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("src/main/java/com/example/file.json"));

            JSONArray trainersArray = (JSONArray) jsonObject.get("trainers");

            trainerStorage = new HashMap<>();
            for (Object trainerObject : trainersArray) {
                JSONObject trainerJson = (JSONObject) trainerObject;
                long id = (Long) trainerJson.get("id");
                long specializationId = (Long) trainerJson.get("specialization-id");
                long userId = (Long) trainerJson.get("user-id");

                Trainer trainer = new Trainer((int) id, (int) specializationId, (int) userId);
                trainerStorage.put(id, trainer);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

//        Map<Long, Trainer> trainerStorage = new HashMap<>();
//        Trainer initExample = new Trainer(1, 1, 1);
//        trainerStorage.put((long) initExample.getId(), initExample);
        return trainerStorage;
    }
}
