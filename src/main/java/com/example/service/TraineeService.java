package com.example.service;

import com.example.Main;
import com.example.entity.*;
import com.example.exceptions.UnupdatableException;
import com.example.repo.*;
import com.example.rest.request.TraineeRegistrationRequest;
import com.example.rest.request.TraineeUpdateRequest;
import com.example.rest.request.TrainingGetRequest;
import com.example.rest.response.TrainingResponse;
import io.prometheus.client.Counter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TraineeService {

    private final TraineeRepository repository;
    private final UserRepository userRepository;
    private final TrainingRepository trainingRepository;
    private final TrainingTypeRepository trainingTypeRepository;
    private final TrainerRepository trainerRepository;
    private final Counter customCounter;
    private final Map<Integer, String> temporaryPasswords = new HashMap<>();

    public String getTemporaryPassword(Integer userId) {
        return temporaryPasswords.get(userId); // Retrieve the temporary password by user ID
    }

    @Autowired
    public TraineeService(UserRepository userRepository,
                          TraineeRepository traineeRepository,
                          TrainingRepository trainingRepository,
                          TrainingTypeRepository trainingTypeRepository,
                          TrainerRepository trainerRepository,
                          Counter customCounter) {
        repository = traineeRepository;
        this.userRepository = userRepository;
        this.trainingRepository = trainingRepository;
        this.trainingTypeRepository = trainingTypeRepository;
        this.trainerRepository = trainerRepository;
        this.customCounter = customCounter;
    }

    @Transactional(readOnly = true)
    public List<Trainee> list(String username, String password) {
        return repository.findAll();
    }

    @Transactional
    public Trainee create(TraineeRegistrationRequest request) {
        User user = new User(request.getFirstName(), request.getLastName(), true); // Assuming isActive is set to true
        String rawPassword = user.getPassword();
        userRepository.create(user);
        temporaryPasswords.put(user.getId(), rawPassword);

        Trainee trainee = new Trainee();
        trainee.setDateOfBirth(request.getDateOfBirth());
        trainee.setAddress(request.getAddress());
        trainee.setUserId(user.getId());

        return repository.create(trainee);
    }

    @Transactional(readOnly = true)
    public Optional<Trainee> get(int id) {
        return repository.get(id);
    }

    @Transactional
    public void delete(int id) {
        for (Training training : repository.getTraineeTrainings(id)) {
            trainingTypeRepository.delete(training.getTrainingTypeId());
            trainingRepository.delete(training.getId());
        }
        int userId = repository.get(id).get().getUserId();
        repository.delete(id);
        customCounter.inc();
        userRepository.delete(userId);
    }

    @Transactional
    public Trainee update(TraineeUpdateRequest request) throws UnupdatableException {
        Trainee trainee = getTraineeByUsername(request.getUsername()).orElseThrow(() -> new UnupdatableException("Trainee is null"));

        trainee.setAddress(request.getAddress());
        User user = trainee.getGym_user();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setActive(request.getActive());

        return repository.update(trainee);
    }

    @Transactional(readOnly = true)
    public Optional<Trainee> getTraineeByUsername(String username) {
        return repository.getTraineeByUsername(username);
    }

    @Transactional
    public void changeTraineePassword(int traineeId, String newPassword) {
        userRepository.changeUserPassword(repository.get(traineeId).get().getUserId(), newPassword);
    }

    @Transactional
    public void activateTrainee(int traineeId) {
        userRepository.activateUser(repository.get(traineeId).get().getUserId());
    }

    @Transactional
    public void deactivateTrainee(int traineeId) {
        userRepository.deactivateUser(repository.get(traineeId).get().getUserId());
    }

    @Transactional
    public void changeStatus(String username, Boolean status) {
        Trainee trainee = getTraineeByUsername(username).orElseThrow(() -> new RuntimeException("Trainee not found"));
        if (status) {
            activateTrainee(trainee.getId());
        } else {
            deactivateTrainee(trainee.getId());
        }
    }

    @Transactional
    public void deleteTraineeByUsername(String username, String password) {
        int id = repository.getTraineeByUsername(username).get().getId();
        for (Training training : repository.getTraineeTrainings(id)) {
            trainingTypeRepository.delete(training.getTrainingTypeId());
            trainingRepository.delete(training.getId());
        }
        int userId = repository.get(id).get().getUserId();
        repository.delete(id);
        userRepository.delete(userId);
    }

    @Transactional
    public void addTrainingToTrainee(Trainee trainee, Training training) {
        training.setTraineeId(trainee.getId());
        trainingRepository.create(training);
    }

    @Transactional(readOnly = true)
    public List<TrainingResponse> getTraineeTrainings(TrainingGetRequest request) {
        Trainee trainee = getTraineeByUsername(request.getUsername()).orElseThrow(()-> new RuntimeException("Trainee not found"));
        Integer typeId = (request.getTrainingType() == null) ? 0 : trainingTypeRepository.getByName(request.getTrainingType()).orElseThrow().getId();
        Integer trainerId = (request.getTrainerName() == null) ? 0 : trainerRepository.getTrainerByUsername(request.getTrainerName()).orElseThrow().getId();
        List<Training> list = repository.getTraineeTrainings(trainee.getId(), request.getFromDate(), request.getToDate(), trainerId, typeId);

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
        Trainee trainee = getTraineeByUsername(username).orElseThrow(() -> new UnupdatableException("Trainee not found"));
        List<Trainer> list = new ArrayList<>();
        for(String trainerUsername : listOfTrainersUsername) {
            Trainer trainer = trainerRepository.getTrainerByUsername(trainerUsername).orElseThrow(()-> new UnupdatableException("Trainer not found"));
            trainer.getTrainees().add(trainee);
            list.add(trainer);
        }

        repository.addTrainersToTrainee(trainee.getId(), list);

        return trainee.getTrainers();
    }
}