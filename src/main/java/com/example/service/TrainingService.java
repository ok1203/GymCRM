package com.example.service;

import com.example.entity.Trainee;
import com.example.entity.Trainer;
import com.example.entity.Training;
import com.example.entity.TrainingType;
import com.example.repo.TraineeRepository;
import com.example.repo.TrainerRepository;
import com.example.repo.TrainingRepository;
import com.example.repo.TrainingTypeRepository;
import com.example.rest.request.TrainingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TrainingService {

    private TrainingRepository repository;
    private TrainerRepository trainerRepository;
    private TraineeRepository traineeRepository;
    private TrainingTypeRepository trainingTypeRepository;

    @Autowired
    public TrainingService(TrainingRepository trainingRepository,
                           TrainerRepository trainerRepository,
                           TraineeRepository traineeRepository,
                           TrainingTypeRepository trainingTypeRepository) {
        repository = trainingRepository;
        this.trainerRepository = trainerRepository;
        this.traineeRepository = traineeRepository;
        this.trainingTypeRepository = trainingTypeRepository;
    }

    @Transactional(readOnly = true)
    public List<Training> list() {
        return repository.findAll();
    }

    @Transactional
    public Training create(TrainingRequest request) {
        Training training = new Training();
        Trainer trainer = trainerRepository.getTrainerByUsername(request.getTrainerName(), "").orElseThrow(() -> new RuntimeException("Trainer not found"));
        training.setTrainerId(trainer.getId());
        Trainee trainee = traineeRepository.getTraineeByUsername(request.getTraineeName(), "").orElseThrow(() -> new RuntimeException("Trainee not found"));
        training.setTraineeId(trainee.getId());
        training.setDate(request.getDate());
        training.setDuration(request.getDuration());
        training.setName(request.getTrainingName());
        TrainingType trainingType = trainingTypeRepository.getByName(request.getTrainingName()).orElse(null);
        if (trainingType == null) {
            trainingType = new TrainingType(request.getTrainingName());
            trainingTypeRepository.create(trainingType);
        }
        training.setTrainingTypeId(trainingTypeRepository.getByName(request.getTrainingName()).get().getId());
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
