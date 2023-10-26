package com.example.service;

import com.example.model.Training;
import com.example.repo.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainingService {

    private TrainingRepository repository;

    @Autowired
    public TrainingService(TrainingRepository trainingRepository) {
        repository = trainingRepository;
    }

    public List<Training> list() {
        return repository.findAll();
    }

    public Training create(Training training) {
        return repository.create(training);
    }

    public Optional<Training> get(int id) {
        return repository.get(id);
    }
}
