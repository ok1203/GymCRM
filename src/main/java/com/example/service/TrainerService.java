package com.example.service;

import com.example.model.Trainer;
import com.example.repo.TrainerRepository;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

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
        repository.findAll().put((long) trainer.getId(), trainer);
        return get(trainer.getId());
    }

    @Override
    public Trainer get(int id) {
        return repository.findAll().get((long) id);
    }

    public Trainer update(Trainer trainer) throws IOException, ParseException {
        if (get(trainer.getId()) != null) {
            repository.findAll().replace((long)trainer.getId(), trainer);
        } else {
            return null;
        }
        return get(trainer.getId());
    }
}
