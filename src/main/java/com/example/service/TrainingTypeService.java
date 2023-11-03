package com.example.service;

import com.example.entity.TrainingType;
import com.example.repo.TrainingTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TrainingTypeService {

    private TrainingTypeRepository repository;

    @Autowired
    public TrainingTypeService(TrainingTypeRepository trainingTypeRepository) {
        repository = trainingTypeRepository;
    }

    @Transactional(readOnly = true)
    public List<TrainingType> list() {
        return repository.findAll();
    }

    @Transactional
    public TrainingType create(TrainingType trainingType) {
        return null;
    }

    @Transactional
    public Optional<TrainingType> get(int id) {
        return null;
    }
}
