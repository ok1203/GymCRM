package com.example.service;

import com.example.model.Training;
import com.example.repo.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TrainingService implements CrudService<Training> {

    private TrainingRepository repository;

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
        repository.findAll().put((long) training.getId(), training);
        return get(training.getId());
    }

    @Override
    public Training get(int id) {
        return repository.findAll().get((long) id);
    }
}
