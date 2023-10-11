package com.example.service;

import com.example.model.Training;
import com.example.repo.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class TrainingService implements CrudService<Training> {

    TrainingRepository repository;

    @Autowired
    public TrainingService(TrainingRepository trainingRepository) {
        repository = trainingRepository;
    }

    @Override
    public Map<Long, Training> list() {
        return repository.findAll();
    }

    @Override
    public Training create(Training training) {
        return null;
    }

    @Override
    public Optional<Training> get(int id) {
        return Optional.empty();
    }
}
