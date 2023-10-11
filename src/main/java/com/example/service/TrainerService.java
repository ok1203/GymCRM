package com.example.service;

import com.example.model.Trainer;
import com.example.repo.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class TrainerService implements CrudService<Trainer> {

    TrainerRepository repository;

    @Autowired
    public TrainerService(TrainerRepository trainerRepository) {
        repository = trainerRepository;
    }

    @Override
    public Map<Long, Trainer> list() {
        return repository.findAll();
    }

    @Override
    public Trainer create(Trainer trainer) {
        return null;
    }

    @Override
    public Optional<Trainer> get(int id) {
        return Optional.empty();
    }
}
