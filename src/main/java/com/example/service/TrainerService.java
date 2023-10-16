package com.example.service;

import com.example.model.Trainer;
import com.example.repo.TrainerRepository;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    public Map<Long, Trainer> list() {
        return repository.findAll();
    }

    @Override
    public Trainer create(Trainer trainer) {
        repository.create(trainer);
        return get(trainer.getId()).get();
    }

    @Override
    public Optional<Trainer> get(int id) {
        return Optional.ofNullable(repository.get(id));
    }

    public Trainer update(Trainer trainer) {
        return repository.update(trainer);
    }
}
