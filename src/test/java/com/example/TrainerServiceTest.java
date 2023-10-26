package com.example;

import com.example.model.Trainee;
import com.example.model.Trainer;
import com.example.model.Training;
import com.example.repo.TrainerRepository;
import com.example.service.TrainerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainerServiceTest {

    @Mock
    private TrainerRepository repository;

    @InjectMocks
    private TrainerService service;

    @Test
    public void testGetId() {
        int expectedId = 1;
        Trainer trainer = new Trainer();
        trainer.setId(expectedId);

        when(repository.get(expectedId, "username", "password")).thenReturn(Optional.of(trainer));
        int actualId = service.get(expectedId, "username", "password").get().getId();

        assertEquals(expectedId, actualId);
    }

    @Test
    public void testGetSpecializationId() {
        int expectedSpecializationId = 2;
        Trainer trainer = new Trainer();
        trainer.setSpecializationId(expectedSpecializationId);

        when(repository.get(1, "username", "password")).thenReturn(Optional.of(trainer));
        int actualSpecializationId = service.get(1, "username", "password").get().getSpecializationId();

        assertEquals(expectedSpecializationId, actualSpecializationId);
    }

    @Test
    public void testGetUserId() {
        int expectedUserId = 3;
        Trainer trainer = new Trainer();
        trainer.setUserId(expectedUserId);

        when(repository.get(1, "username", "password")).thenReturn(Optional.of(trainer));
        int actualUserId = service.get(1, "username", "password").get().getUserId();

        assertEquals(expectedUserId, actualUserId);
    }

    @Test
    public void testGetTraineeByUsername() {
        String username = "testUser";
        String password = "testPassword";

        Trainer expectedTrainer = new Trainer();

        when(repository.getTrainerByUsername(username, password)).thenReturn(Optional.of(expectedTrainer));

        Optional<Trainer> actualTrainee = service.getTrainerByUsername(username, password);

        assertEquals(expectedTrainer, actualTrainee.orElse(null));
    }

    @Test
    public void testChangeTrainerPassword() {
        int trainerId = 1;
        String newPassword = "newPassword";
        String username = "username";
        String password = "password";

        service.changeTrainerPassword(trainerId, newPassword, username, password);

        verify(repository).changeTrainerPassword(trainerId, newPassword, username, password);
    }

    @Test
    public void testDeleteTrainer() {
        int trainerId = 1;
        String username = "username";
        String password = "password";

        service.deleteTrainer(trainerId, username, password);

        verify(repository).deleteTrainer(trainerId, username, password);
    }

    @Test
    public void testActivateTrainer() {
        int trainerId = 1;
        String username = "username";
        String password = "password";

        service.activateTrainer(trainerId, username, password);

        verify(repository).activateTrainer(trainerId, username, password);
    }

    @Test
    public void testDeactivateTrainer() {
        int trainerId = 1;
        String username = "username";
        String password = "password";

        service.deactivateTrainer(trainerId, username, password);

        verify(repository).deactivateTrainer(trainerId, username, password);
    }

    @Test
    public void testAddTrainingToTrainer() {
        Trainer trainer = new Trainer();
        Training training = new Training();
        String username = "username";
        String password = "password";

        service.addTrainingToTrainer(trainer, training, username, password);

        verify(repository).addTrainingToTrainer(trainer, training, username, password);
    }

    @Test
    public void testGetNotAssignedActiveTrainersForTrainee() {
        Trainee trainee = new Trainee();
        String username = "username";
        String password = "password";

        List<Trainer> expectedTrainers = new ArrayList<>(); // Populate with expected trainers

        when(repository.getNotAssignedActiveTrainersForTrainee(trainee, username, password)).thenReturn(expectedTrainers);

        List<Trainer> actualTrainers = service.getNotAssignedActiveTrainersForTrainee(trainee, username, password);

        assertEquals(expectedTrainers, actualTrainers);
    }

    @Test
    public void testGetTrainerTrainings() {
        int trainerId = 1;
        String username = "username";
        String password = "password";

        List<Training> expectedTrainings = new ArrayList<>(); // Populate with expected trainings

        when(repository.getTrainerTrainings(trainerId, username, password)).thenReturn(expectedTrainings);

        List<Training> actualTrainings = service.getTrainerTrainings(trainerId, username, password);

        assertEquals(expectedTrainings, actualTrainings);
    }

}
