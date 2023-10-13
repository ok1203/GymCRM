package com.example.service;

import com.example.model.Trainee;
import com.example.repo.TraineeRepository;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

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
    public Trainee create(Trainee trainee) throws IOException, ParseException {
        repository.findAll().put((long) trainee.getId(), trainee);
        return get(trainee.getId());
    }

    @Override
    public Trainee get(int id) throws IOException, ParseException {
        return repository.findAll().get((long) id);
    }

    public void delete(int id) throws IOException, ParseException {
        repository.findAll().remove((long) id);
    }

    public Trainee update(Trainee trainee) throws IOException, ParseException {
        if (get(trainee.getId()) != null) {
            repository.findAll().replace((long)trainee.getId(), trainee);
        } else {
            return null;
        }
        return get(trainee.getId());
    }
}
