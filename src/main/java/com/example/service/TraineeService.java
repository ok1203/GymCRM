package com.example.service;

import com.example.model.Trainee;
import com.example.repo.TraineeRepository;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Service
public class TraineeService implements CrudService<Trainee> {

    private TraineeRepository repository;

    @Autowired
    public TraineeService(TraineeRepository traineeRepository) {
        repository = traineeRepository;
    }

    @Override
    public Map<Long, Trainee> list() throws IOException, ParseException {
        return repository.findAll();
    }

    @Override
    public Trainee create(Trainee trainee) {
        return null;
    }

    @Override
    public Optional<Trainee> get(int id) {
        return Optional.empty();
    }
}
