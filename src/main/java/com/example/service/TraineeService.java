package com.example.service;

import com.example.entity.Trainer;
import com.example.entity.User;
import com.example.exceptions.UnupdatableException;
import com.example.entity.Trainee;
import com.example.entity.Training;
import com.example.repo.*;
import com.example.rest.request.TraineeUpdateRequest;
import com.example.rest.request.TrainingGetRequest;
import com.example.rest.response.TrainingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TraineeService {

    private TraineeRepository repository;
    private UserRepository userRepository;
    private TrainingRepository trainingRepository;
    private TrainingTypeRepository trainingTypeRepository;
    private TrainerRepository trainerRepository;

    @Autowired
    public TraineeService(UserRepository userRepository,
                          TraineeRepository traineeRepository,
                          TrainingRepository trainingRepository,
                          TrainingTypeRepository trainingTypeRepository,
                          TrainerRepository trainerRepository) {
        repository = traineeRepository;
        this.userRepository = userRepository;
        this.trainingRepository = trainingRepository;
        this.trainingTypeRepository = trainingTypeRepository;
        this.trainerRepository = trainerRepository;
    }

    @Transactional(readOnly = true)
    public List<Trainee> list(String username, String password) {
        return repository.findAll(username, password);
    }

    @Transactional
    public Trainee create(Trainee trainee) {
        return repository.create(trainee);
    }

    @Transactional(readOnly = true)
    public Optional<Trainee> get(int id, String username, String password) {
        return repository.get(id, username, password);
    }

    @Transactional
    public void delete(int id, String username, String password) {
        for (Training training : repository.getTraineeTrainings(id)) {
            trainingTypeRepository.delete(training.getTrainingTypeId());
            trainingRepository.delete(training.getId());
        }
        int userId = repository.get(id, username, password).get().getUserId();
        repository.delete(id, username, password);
        userRepository.delete(userId);
    }

    @Transactional
    public Trainee update(TraineeUpdateRequest request) throws UnupdatableException {
        Trainee trainee = getTraineeByUsername(request.getUsername(), "").orElseThrow(() -> new UnupdatableException("Trainee is null"));

        trainee.setAddress(request.getAddress());
        User user = trainee.getGym_user();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setActive(request.getActive());

        return repository.update(trainee);
    }

    @Transactional(readOnly = true)
    public Optional<Trainee> getTraineeByUsername(String username, String password) {
        return repository.getTraineeByUsername(username, password);
    }

    @Transactional
    public void changeTraineePassword(int traineeId, String newPassword, String username, String password) {
        userRepository.changeUserPassword(repository.get(traineeId, username, password).get().getUserId(), newPassword);
    }

    @Transactional
    public void activateTrainee(int traineeId) {
        userRepository.activateUser(repository.get(traineeId, "", "").get().getUserId());
    }

    @Transactional
    public void deactivateTrainee(int traineeId) {
        userRepository.deactivateUser(repository.get(traineeId, "", "").get().getUserId());
    }

    @Transactional
    public void changeStatus(String username, Boolean status) {
        Trainee trainee = getTraineeByUsername(username, "").orElseThrow(() -> new RuntimeException("Trainee not found"));
        if (status) {
            activateTrainee(trainee.getId());
        } else {
            deactivateTrainee(trainee.getId());
        }
    }

    @Transactional
    public void deleteTraineeByUsername(String username, String password) {
        int id = repository.getTraineeByUsername(username, password).get().getId();
        for (Training training : repository.getTraineeTrainings(id)) {
            trainingTypeRepository.delete(training.getTrainingTypeId());
            trainingRepository.delete(training.getId());
        }
        int userId = repository.get(id, username, password).get().getUserId();
        repository.delete(id, username, password);
        userRepository.delete(userId);
    }

    @Transactional
    public void addTrainingToTrainee(Trainee trainee, Training training, String username, String password) {
        training.setTraineeId(trainee.getId());
        trainingRepository.create(training);
    }

    @Transactional(readOnly = true)
    public List<TrainingResponse> getTraineeTrainings(TrainingGetRequest request) {
        Trainee trainee = getTraineeByUsername(request.getUsername(), "").orElseThrow(()-> new RuntimeException("Trainee not found"));
        List<Training> list = repository.getTraineeTrainings(trainee.getId());

        if (request.getFromDate() != null)
            list = list
                    .stream()
                    .filter(training -> training.getDate().after(request.getFromDate()))
                    .collect(Collectors.toList());
        if (request.getToDate() != null)
            list = list
                    .stream()
                    .filter(training -> training.getDate().before(request.getToDate()))
                    .collect(Collectors.toList());
        if (request.getTrainerName() != null)
            list = list
                    .stream()
                    .filter(training -> training.getTrainer().getGym_user().getUserName().equals(request.getTrainerName()))
                    .collect(Collectors.toList());
        if (request.getTrainingType() != null)
            list = list
                    .stream()
                    .filter(training -> trainingTypeRepository.get(
                            training.getTrainingTypeId())
                            .orElseThrow(()-> new RuntimeException("Training type not found"))
                            .getTypeName()
                            .equals(request.getTrainingType()))
                    .collect(Collectors.toList());

        List<TrainingResponse> responseList = new ArrayList<>();
        for (Training training: list) {
            TrainingResponse trainingResponse = new TrainingResponse(training,
                    trainingTypeRepository.get(training.getTrainingTypeId()).get().getTypeName(),
                    training.getTrainer().getGym_user().getUserName());
            responseList.add(trainingResponse);
        }

        return responseList;
    }

    @Transactional(readOnly = true)
    public void authenticateTrainee(String username, String password) {
        repository.authenticateTrainee(username, password);
    }

    @Transactional
    public List<Trainer> updateTrainers(String username, List<String> listOfTrainersUsername) {
        Trainee trainee = getTraineeByUsername(username, "").orElseThrow(() -> new UnupdatableException("Trainee not found"));
        List<Trainer> list = new ArrayList<>();
        for(String trainerUsername : listOfTrainersUsername) {
            Trainer trainer = trainerRepository.getTrainerByUsername(trainerUsername,"").orElseThrow(()-> new UnupdatableException("Trainer not found"));
            trainer.getTrainees().add(trainee);
            list.add(trainer);
        }

        repository.addTrainersToTrainee(trainee.getId(), list);

        return trainee.getTrainers();
    }
}