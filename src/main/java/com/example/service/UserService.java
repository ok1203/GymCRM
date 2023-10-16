package com.example.service;

import com.example.model.User;
import com.example.repo.UserRepository;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class UserService implements CrudService<User> {

    private UserRepository repository;

    @Autowired
    public UserService(UserRepository userRepository) {
        repository = userRepository;
    }

    @Override
    public Map<Long, User> list() throws IOException, ParseException {
        return repository.findAll();
    }

    @Override
    public User create(User user) {
        return null;
    }

    @Override
    public Optional<User> get(int id) {
        return null;
    }
}
