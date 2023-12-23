package com.example.service;

import com.example.entity.Trainee;
import com.example.entity.Trainer;
import com.example.entity.Training;
import com.example.entity.TrainingType;
import com.example.repo.TraineeRepository;
import com.example.repo.TrainerRepository;
import com.example.repo.TrainingRepository;
import com.example.repo.TrainingTypeRepository;
import com.example.rest.request.ActionType;
import com.example.rest.request.TrainingRequest;
import com.example.rest.request.TrainingSecondaryRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class TrainingService {

    private TrainingRepository repository;
    private TrainerRepository trainerRepository;
    private TraineeRepository traineeRepository;
    private TrainingTypeRepository trainingTypeRepository;
    @Autowired
    private JmsTemplate jmsTemplate;

    private String WORKLOAD_QUEUE = "workload.queue";

    private Logger log = LoggerFactory.getLogger(TrainingService.class);

//    private String workloadUrl = "http://desktop-h8sautm:9090" + "/trainer/workload";
//
//    RestClient restClient = RestClient.create();

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
        Trainer trainer = trainerRepository.getTrainerByUsername(request.getTrainerName()).orElseThrow(() -> new RuntimeException("Trainer not found"));
        training.setTrainerId(trainer.getId());
        Trainee trainee = traineeRepository.getTraineeByUsername(request.getTraineeName()).orElseThrow(() -> new RuntimeException("Trainee not found"));
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

        TrainingSecondaryRequest secondaryRequest = createSecondaryRequest(request, ActionType.ADD);

        jmsTemplate.convertAndSend( WORKLOAD_QUEUE, secondaryRequest);
        log.info("Message sent to queue: " + WORKLOAD_QUEUE);
//        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//        String jwt = requestAttributes.getRequest().getHeader("Authorization");
//        restClient.post()
//                .uri(workloadUrl)
//                .header("Authorization", jwt)
//                .contentType(APPLICATION_JSON)
//                .body(secondaryRequest)
//                .retrieve();

        return repository.create(training);
    }

    @Transactional(readOnly = true)
    public Optional<Training> get(int id) {
        return repository.get(id);
    }

    @Transactional
    public void delete(int id) {
        TrainingSecondaryRequest secondaryRequest = new TrainingSecondaryRequest();
        Training training = repository.get(id).orElseThrow();
        secondaryRequest.setTrainerUsername(training.getTrainer().getGym_user().getUserName());
        secondaryRequest.setTrainingDate(training.getDate());
        secondaryRequest.setTrainingDuration(training.getDuration());
        secondaryRequest.setActionType(ActionType.DELETE);

        jmsTemplate.convertAndSend( WORKLOAD_QUEUE, secondaryRequest);
        log.info("Message sent to queue: " + WORKLOAD_QUEUE);

//        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//        String jwt = requestAttributes.getRequest().getHeader("Authorization");
//
//        restClient.post()
//                .uri(workloadUrl)
//                .header("Authorization", jwt)
//                .contentType(APPLICATION_JSON)
//                .body(secondaryRequest)
//                .retrieve();
        repository.delete(id);
    }

    private TrainingSecondaryRequest createSecondaryRequest(TrainingRequest request, ActionType actionType) {
        // Create a TrainingSecondaryRequest object from the TrainingRequest object
        TrainingSecondaryRequest secondaryRequest = new TrainingSecondaryRequest();
        secondaryRequest.setTrainerUsername(request.getTrainerName());
        Trainer trainer = trainerRepository.getTrainerByUsername(request.getTrainerName()).orElseThrow();
        secondaryRequest.setTrainerFirstname(trainer.getGym_user().getFirstName());
        secondaryRequest.setTrainerLastname(trainer.getGym_user().getLastName());
        secondaryRequest.setActive(trainer.getGym_user().isActive());
        secondaryRequest.setTrainingDate(request.getDate());
        secondaryRequest.setTrainingDuration(request.getDuration());
        secondaryRequest.setActionType(actionType);
        return secondaryRequest;
    }
}
