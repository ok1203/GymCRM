package com.example;

import com.example.model.Trainee;
import com.example.model.Trainer;
import com.example.model.Training;
import com.example.repo.TrainerRepository;
import com.example.storage.TrainerStorage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainerRepositoryTest {
    @InjectMocks
    private TrainerRepository trainerRepository;

    @Mock
    private TrainerStorage storageComponent;

    @Test
    public void testFindAll() {
        List<Trainer> trainers = new ArrayList<>();
        Trainer trainer1 = new Trainer(1, 1, 1);
        trainers.add(trainer1);

        when(storageComponent.getTrainerMap("username", "password")).thenReturn(trainers);

        List<Trainer> result = trainerRepository.findAll("username", "password");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(trainer1, result.get(0));
    }

    @Test
    public void testCreate() {
        Trainer trainer = new Trainer(1, 1, 1);

        when(storageComponent.createTrainer(any(Trainer.class))).thenReturn(trainer);

        Trainer result = trainerRepository.create(trainer);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(1, result.getSpecializationId());
        assertEquals(1, result.getUserId());
    }

    @Test
    public void testGet() {
        Trainer trainer = new Trainer(1, 1, 1);

        when(storageComponent.getTrainer(1, "username", "password")).thenReturn(Optional.of(trainer));

        Optional<Trainer> result = trainerRepository.get(1, "username", "password");

        assertNotNull(result);
        assertEquals(1, result.get().getId());
        assertEquals(1, result.get().getSpecializationId());
        assertEquals(1, result.get().getUserId());
    }

    @Test
    public void testUpdate() {
        Trainer trainer = new Trainer(1, 1, 1);

        when(storageComponent.updateTrainer(trainer, "username", "password")).thenReturn(trainer);

        Trainer result = trainerRepository.update(trainer, "username", "password");

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(1, result.getSpecializationId());
        assertEquals(1, result.getUserId());
    }

    @Test
    public void testGetTrainerByUsername() {
        Trainer trainer = new Trainer(1, 1, 1);

        when(storageComponent.getTrainerByUsername("username", "password")).thenReturn(Optional.of(trainer));

        Optional<Trainer> result = trainerRepository.getTrainerByUsername("username", "password");

        assertNotNull(result);
        assertEquals(1, result.get().getId());
        assertEquals(1, result.get().getSpecializationId());
        assertEquals(1, result.get().getUserId());
    }

    @Test
    public void testChangeTrainerPassword() {
        trainerRepository.changeTrainerPassword(1, "newPassword", "username", "password");

        verify(storageComponent).changeTrainerPassword(1, "newPassword", "username", "password");
    }

    @Test
    public void testDeleteTrainer() {
        trainerRepository.deleteTrainer(1, "username", "password");

        verify(storageComponent).deleteTrainer(1, "username", "password");
    }

    @Test
    public void testActivateTrainer() {
        trainerRepository.activateTrainer(1, "username", "password");

        verify(storageComponent).activateTrainer(1, "username", "password");
    }

    @Test
    public void testDeactivateTrainer() {
        trainerRepository.deactivateTrainer(1, "username", "password");

        verify(storageComponent).deactivateTrainer(1, "username", "password");
    }

    @Test
    public void testGetNotAssignedActiveTrainersForTrainee() {
        List<Trainer> trainers = new ArrayList<>();
        Trainer trainer1 = new Trainer(1, 1, 1);
        trainers.add(trainer1);

        Trainee trainee = new Trainee(new Date(2000, 11, 11), "Address1", 1);

        when(storageComponent.getNotAssignedActiveTrainersForTrainee(trainee, "username", "password")).thenReturn(trainers);

        List<Trainer> result = trainerRepository.getNotAssignedActiveTrainersForTrainee(trainee, "username", "password");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(trainer1, result.get(0));
    }

    @Test
    public void testAddTrainingToTrainer() {
        Trainer trainer = new Trainer(1, 1, 1);
        Training training = new Training(1, 1, "Training1", 1, new Date(), 60);

        trainerRepository.addTrainingToTrainer(trainer, training, "username", "password");

        verify(storageComponent).addTrainingToTrainer(trainer, training, "username", "password");
    }

    @Test
    public void testGetTrainerTrainings() {
        List<Training> trainings = new ArrayList<>();
        Training training = new Training(1, 1, "Training1", 1, new Date(), 60);
        trainings.add(training);

        when(storageComponent.getTrainerTrainings(1, "username", "password")).thenReturn(trainings);

        List<Training> result = trainerRepository.getTrainerTrainings(1, "username", "password");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(training, result.get(0));
    }
}
