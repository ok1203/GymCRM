package com.example.repo;

import com.example.annotation.Storage;
import com.example.model.User;
import com.example.storage.StorageComponent;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Map;

@Repository
public class UserRepository implements CrudRepository<User> {

    @Storage
    private StorageComponent storageComponent;

    @Override
    public Map<Long, User> findAll() throws IOException, ParseException {

        return storageComponent.getUsersMap();

    }
}
