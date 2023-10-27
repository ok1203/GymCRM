package com.example.repo;

import com.example.entity.User;
import com.example.storage.UserStorage;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    @Autowired
    private UserStorage userStorage;

    public List<User> findAll() throws IOException, ParseException {
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

    public void changeUserPassword(int userId, String newPassword) {
        userStorage.changeUserPassword(userId, newPassword);
    }

    public void activateUser(int userId) {
        userStorage.activateUser(userId);
    }

    public void deactivateUser(int userId) {
        userStorage.deactivateUser(userId);
    }
}
