package com.example.repo;

import com.example.model.User;
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
public class UserRepository implements CrudRepository<User> {

    @Override
    public Map<Long, User> findAll() throws IOException, ParseException {
        Map<Long, User> userStorage = null;
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("src/main/java/com/example/file.json"));

            JSONArray usersArray = (JSONArray) jsonObject.get("users");

            userStorage = new HashMap<>();
            for (Object userObject : usersArray) {
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

        } catch (Exception e) {
            e.printStackTrace();
        }
//        User initExample = new User(1,
//                "John",
//                "Johnson",
//                true);
//        userStorage.put((long) initExample.getId(), initExample);
        return userStorage;
    }
}
