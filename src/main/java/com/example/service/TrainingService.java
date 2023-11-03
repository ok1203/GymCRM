package com.example.service;

import com.example.entity.Training;
import com.example.repo.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TrainingService {

    private TrainingRepository repository;

    @Autowired
    public TrainingService(TrainingRepository trainingRepository) {
        repository = trainingRepository;
    }

    @Transactional(readOnly = true)
    public List<Training> list() {
        return repository.findAll();
    }

    @Transactional
    public Training create(Training training) {
        return repository.create(training);
    }

    @Transactional(readOnly = true)
    public Optional<Training> get(int id) {
        return repository.get(id);
    }

    @Transactional
    public void delete(int id) {
        repository.delete(id);
    }
}
