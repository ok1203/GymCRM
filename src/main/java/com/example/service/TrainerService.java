package com.example.service;

import com.example.model.Trainer;
import com.example.repo.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class TrainerService implements CrudService<Trainer> {

    private TrainerRepository repository;

    @Autowired
    public TrainerService(TrainerRepository trainerRepository) {
        repository = trainerRepository;
    }

    @Override
    public Map<Integer, Trainer> list() {
        return repository.findAll();
    }

    @Override
    public Trainer create(Trainer trainer) {
        return repository.create(trainer);
    }

    @Override
    public Optional<Trainer> get(int id) {
        return repository.get(id);
    }

    public Trainer update(Trainer trainer) {
        if (get(trainer.getId()).isEmpty()) {
            throw new NullPointerException("Trainer is null");
        }
        return repository.update(trainer);
    }
}
