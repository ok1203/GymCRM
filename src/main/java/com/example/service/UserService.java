package com.example.service;

import com.example.model.Training;
import com.example.model.User;
import com.example.repo.UserRepository;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class UserService {

    private UserRepository repository;

    @Autowired
    public UserService(UserRepository userRepository) {
        repository = userRepository;
    }

    public List<User> list() throws IOException, ParseException {
        return repository.findAll();
    }

    public User create(User user) {
        return repository.create(user);
    }

    public Optional<User> get(int id) {
        return repository.get(1);
    }

    public void delete(int id) {
        repository.delete(id);
    }
}
