package com.example.repo;

import com.example.annotation.Storage;
import com.example.model.User;
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
public class UserRepository implements CrudRepository<User> {

    Map<Long, User> userStorage = null;

    @Storage
    private StorageComponent storageComponent;

    public UserRepository() throws IOException, ParseException {
    }

    @Override
    public Map<Long, User> findAll() throws IOException, ParseException {

        userStorage = new HashMap<>();
        for (Object userObject : storageComponent.getArray("users")) {
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
}
