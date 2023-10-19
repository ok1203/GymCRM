package com.example.repo.service;

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
    public Map<Integer, Trainee> list() throws IOException, ParseException {
        return repository.findAll();
    }

    @Override
    public Trainee create(Trainee trainee) {
        return repository.create(trainee);
    }

    @Override
    public Optional<Trainee> get(int id) throws IOException, ParseException {
        return repository.get(id);
    }

    public void delete(int id) throws IOException, ParseException {
        repository.delete(id);
    }

    public Trainee update(Trainee trainee) throws IOException, ParseException {
        if (get(trainee.getId()).isEmpty()) {
            throw new NullPointerException("trainee is null");
        }
        return repository.update(trainee);
    }
}
