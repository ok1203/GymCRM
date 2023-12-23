package com.example.service;

import com.example.entity.User;
import com.example.exceptions.UnupdatableException;
import com.example.entity.Trainee;
import com.example.entity.Trainer;
import com.example.entity.Training;
import com.example.repo.*;
import com.example.rest.request.TrainerRegistrationRequest;
import com.example.rest.request.TrainerUpdateRequest;
import com.example.rest.request.TrainingGetRequest;
import com.example.rest.response.TrainingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TrainerService {

    private TrainerRepository repository;
    private UserRepository userRepository;
    private TrainingRepository trainingRepository;
    private TrainingTypeRepository trainingTypeRepository;
    private TraineeRepository traineeRepository;
    private final Map<Integer, String> temporaryPasswords = new HashMap<>();

    public String getTemporaryPassword(Integer userId) {
        return temporaryPasswords.get(userId); // Retrieve the temporary password by user ID
    }

    @Autowired
    public TrainerService(TrainerRepository trainerRepository,
                          UserRepository userRepository,
                          TrainingRepository trainingRepository,
                          TrainingTypeRepository trainingTypeRepository,
                          TraineeRepository traineeRepository) {
        repository = trainerRepository;
        this.userRepository = userRepository;
        this.trainingRepository = trainingRepository;
        this.trainingTypeRepository = trainingTypeRepository;
        this.traineeRepository = traineeRepository;
    }

    @Transactional(readOnly = true)
    public List<Trainer> list() {
        return repository.findAll();
    }

    @Transactional
    public Trainer create(TrainerRegistrationRequest request) {
        User user = new User(request.getFirstName(), request.getLastName(), true);
        String rawPassword = user.getPassword();
        userRepository.create(user);
        temporaryPasswords.put(user.getId(), rawPassword);

        Trainer trainer = new Trainer();
        trainer.setSpecializationId(request.getSpecializationId());
        trainer.setUserId(user.getId());

        return repository.create(trainer);
    }

    @Transactional(readOnly = true)
    public Optional<Trainer> get(int id) {
        return repository.get(id);
    }

    @Transactional
    public Trainer update(TrainerUpdateRequest request) throws UnupdatableException {
        Trainer trainer = getTrainerByUsername(request.getUsername()).orElseThrow(() -> new UnupdatableException("Trainer is null"));

        trainer.setSpecializationId(request.getSpecializationId());
        User user = trainer.getGym_user();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setActive(request.getActive());

        return repository.update(trainer);
    }

    @Transactional(readOnly = true)
    public Optional<Trainer> getTrainerByUsername(String username) {
        return repository.getTrainerByUsername(username);
    }

    @Transactional
    public void changeTrainerPassword(int trainerId, String newPassword) {
        userRepository.changeUserPassword(repository.get(trainerId).get().getUserId(), newPassword);
    }

    @Transactional
    public void delete(int trainerId) {
        for (Training training : repository.getTrainerTrainings(trainerId)) {
            trainingTypeRepository.delete(training.getTrainingTypeId());
            trainingRepository.delete(training.getId());
        }
        repository.delete(trainerId);
    }

    @Transactional
    public void activateTrainer(int trainerId) {
        userRepository.activateUser(repository.get(trainerId).get().getUserId());
    }

    @Transactional
    public void deactivateTrainer(int trainerId) {
        userRepository.deactivateUser(repository.get(trainerId).get().getUserId());
    }

    @Transactional
    public void changeStatus(String username, Boolean status) {
        Trainer trainer = getTrainerByUsername(username).orElseThrow(() -> new RuntimeException("Trainer not found"));
        if (status) {
            activateTrainer(trainer.getId());
        } else {
            deactivateTrainer(trainer.getId());
        }
    }

    @Transactional(readOnly = true)
    public List<Trainer> getNotAssignedActiveTrainersForTrainee(String username) {
        Trainee trainee = traineeRepository.getTraineeByUsername(username).orElseThrow(() -> new UnupdatableException("Trainer is null"));
        List<Trainer> list = repository.getNotAssignedTrainersForTrainee(trainee)
                .stream()
                .filter(trainer -> trainer.getGym_user().isActive())
                .collect(Collectors.toList());
        return list;
    }

    @Transactional
    public void addTrainingToTrainer(Trainer trainer, Training training) {
        training.setTrainerId(trainer.getId());
        trainingRepository.create(training);
    }

    @Transactional(readOnly = true)
    public List<TrainingResponse> getTrainerTrainings(TrainingGetRequest request) {
        Trainer trainer = getTrainerByUsername(request.getUsername()).orElseThrow(()-> new RuntimeException("Trainer not found"));
        Integer traineeId = (request.getTraineeName() == null) ? 0 : traineeRepository.getTraineeByUsername(request.getTraineeName()).orElseThrow().getId();
        List<Training> list = repository.getTrainerTrainings(trainer.getId(), request.getFromDate(), request.getToDate(), traineeId);

        List<TrainingResponse> responseList = new ArrayList<>();
        for (Training training: list) {
            TrainingResponse trainingResponse = new TrainingResponse(training,
                    trainingTypeRepository.get(training.getTrainingTypeId()).get().getTypeName(),
                    training.getTrainee().getGym_user().getUserName());
            responseList.add(trainingResponse);
        }

        return responseList;
    }

    @Transactional(readOnly = true)
    public void authenticateTrainer(String username, String password) {
        repository.authenticateTrainer(username, password);
    }

}
