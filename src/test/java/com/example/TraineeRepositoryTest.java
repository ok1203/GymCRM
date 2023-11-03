package com.example;

import com.example.entity.Trainee;
import com.example.entity.Training;
import com.example.repo.TraineeRepository;
import com.example.storage.TraineeStorage;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TraineeRepositoryTest {
    @InjectMocks
    private TraineeRepository traineeRepository;

    @Mock
    private TraineeStorage storageComponent;

    @Test
    public void testFindAll() throws IOException, ParseException {
        List<Trainee> trainees = new ArrayList<>();
        Trainee trainee1 = new Trainee(new Date(2000, 11, 11), "Address1", 1);
        trainees.add(trainee1);

        when(storageComponent.getTraineeMap("username", "password")).thenReturn(trainees);

        List<Trainee> result = traineeRepository.findAll("username", "password");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(trainee1, result.get(0));
    }

    @Test
    public void testCreate() {
        Trainee trainee = new Trainee(new Date(2000, 11, 11), "Address1", 1);

        when(storageComponent.createTrainee(any(Trainee.class))).thenReturn(trainee);

        Trainee result = traineeRepository.create(trainee);

        assertNotNull(result);
        assertEquals(0, result.getId());
        assertEquals(new Date(2000, 11, 11), result.getDateOfBirth());
        assertEquals("Address1", result.getAddress());
        assertEquals(1, result.getUserId());
    }

    @Test
    public void testGet() {
        Trainee trainee = new Trainee(new Date(2000, 11, 11), "Address1", 1);

        when(storageComponent.getTrainee(1, "username", "password")).thenReturn(Optional.of(trainee));

        Optional<Trainee> result = traineeRepository.get(1, "username", "password");

        assertNotNull(result);
        assertEquals(0, result.get().getId());
        assertEquals(new Date(2000, 11, 11), result.get().getDateOfBirth());
        assertEquals("Address1", result.get().getAddress());
        assertEquals(1, result.get().getUserId());
    }

    @Test
    public void testUpdate() {
        Trainee trainee = new Trainee(new Date(2000, 11, 11), "Address1", 1);

        when(storageComponent.updateTrainee(trainee, "username", "password")).thenReturn(trainee);

        Trainee result = traineeRepository.update(trainee, "username", "password");

        assertNotNull(result);
        assertEquals(0, result.getId());
        assertEquals(new Date(2000, 11, 11), result.getDateOfBirth());
        assertEquals("Address1", result.getAddress());
        assertEquals(1, result.getUserId());
    }

    @Test
    public void testDelete() {
        traineeRepository.delete(1, "l", "");

        verify(storageComponent).deleteTrainee(1, "l", "");
    }

    @Test
    public void testGetTraineeByUsername() {
        Trainee trainee = new Trainee(new Date(2000, 11, 11), "Address1", 1);

        when(storageComponent.getTraineeByUsername("username", "password")).thenReturn(Optional.of(trainee));

        Optional<Trainee> result = traineeRepository.getTraineeByUsername("username", "password");

        assertNotNull(result);
        assertEquals(0, result.get().getId());
        assertEquals(new Date(2000, 11, 11), result.get().getDateOfBirth());
        assertEquals("Address1", result.get().getAddress());
        assertEquals(1, result.get().getUserId());
    }

    @Test
    public void testGetTraineeTrainings() {
        List<Training> trainings = new ArrayList<>();
        Training training = new Training(1, 1, "Training1", 1, new Date(), 60);
        trainings.add(training);

        when(storageComponent.getTraineeTrainings(1, "username", "password")).thenReturn(trainings);

        List<Training> result = traineeRepository.getTraineeTrainings(1, "username", "password");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(training, result.get(0));
    }
}
