package com.example.service;

import com.example.entity.User;
import com.example.repo.UserRepository;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

@Service
public class UserService {

    private UserRepository repository;

    @Autowired
    public UserService(UserRepository userRepository) {
        repository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<User> list() throws IOException, ParseException {
        return repository.findAll();
    }

    @Transactional
    public User create(User user) {
        return repository.create(user);
    }

    @Transactional(readOnly = true)
    public Optional<User> get(int id) {
        return repository.get(id);
    }

    @Transactional
    public void delete(int id) {
        repository.delete(id);
    }
}
