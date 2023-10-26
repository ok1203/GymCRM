package com.example.service;

import com.example.model.Training;
import com.example.model.TrainingType;
import com.example.repo.TrainingTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainingTypeService {

    private TrainingTypeRepository repository;

    @Autowired
    public TrainingTypeService(TrainingTypeRepository trainingTypeRepository) {
        repository = trainingTypeRepository;
    }

    public List<TrainingType> list() {
        return repository.findAll();
    }

    public TrainingType create(TrainingType trainingType) {
        return null;
    }

    public Optional<TrainingType> get(int id) {
        return null;
    }
}
