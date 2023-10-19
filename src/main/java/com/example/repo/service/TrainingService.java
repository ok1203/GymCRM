package com.example.repo.service;

import com.example.model.Training;
import com.example.repo.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class TrainingService implements CrudService<Training> {

    private TrainingRepository repository;

    @Autowired
    public TrainingService(TrainingRepository trainingRepository) {
        repository = trainingRepository;
    }

    @Override
    public Map<Integer, Training> list() {
        return repository.findAll();
    }

    @Override
    public Training create(Training training) {
        return repository.create(training);
    }

    @Override
    public Optional<Training> get(int id) {
        return repository.get(id);
    }
}
