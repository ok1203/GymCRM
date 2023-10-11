package com.example.service;

import com.example.model.TrainingType;
import com.example.repo.TrainingTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class TrainingTypeService implements CrudService<TrainingType> {

    TrainingTypeRepository repository;

    @Autowired
    public TrainingTypeService(TrainingTypeRepository trainingTypeRepository) {
        repository = trainingTypeRepository;
    }

    @Override
    public Map<Long, TrainingType> list() {
        return repository.findAll();
    }

    @Override
    public TrainingType create(TrainingType trainingType) {
        return null;
    }

    @Override
    public Optional<TrainingType> get(int id) {
        return Optional.empty();
    }
}
