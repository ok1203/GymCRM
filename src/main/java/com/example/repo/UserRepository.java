package com.example.repo;

import com.example.model.User;
import com.example.storage.StorageComponent;
import com.example.storage.UserStorage;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepository implements CrudRepository<User> {

    @Autowired
    private UserStorage userStorage;

    @Override
    public Map<Integer, User> findAll() throws IOException, ParseException {
        return userStorage.getAllUsers();
    }

    public User create(User user) {
        return userStorage.saveUser(user);
    }

    public Optional<User> get(int id) {
        return userStorage.getUserById(id);
    }

    public void delete(int id) {
        userStorage.deleteUser(id);
    }
}
