package com.example;

import com.example.model.Training;
import com.example.repo.TrainingRepository;
import com.example.storage.StorageComponent;
import com.example.storage.TrainingStorage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainingRepositoryTest {

    @InjectMocks
    private TrainingRepository trainingRepository;

    @Mock
    private TrainingStorage storageComponent;

    @Test
    public void testFindAll() {
        List <Training> trainingMap = new ArrayList<>();
        Training training1 = new Training(1, 1, "Test Training", 1, null, 60);
        trainingMap.add(training1);

        when(storageComponent.getTrainingMap()).thenReturn(trainingMap);

        List <Training> result = trainingRepository.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(training1, result.get(0));
    }

    @Test
    public void testCreate() {
        Training training = new Training(1, 1, "Test Training", 1, null, 60);

        when(storageComponent.createTraining(any(Training.class))).thenReturn(training);

        Training result = trainingRepository.create(training);

        assertNotNull(result);
        assertEquals(0, result.getId());
        assertEquals(1, result.getTraineeId());
        assertEquals(1, result.getTrainerId());
        assertEquals(1, result.getTrainingTypeId());
    }

    @Test
    public void testGet() {
        Training training = new Training(1, 1, "Test Training", 1, null, 60);

        when(storageComponent.getTraining(1)).thenReturn(Optional.of(training));

        Optional<Training> result = trainingRepository.get(1);

        assertNotNull(result);
        assertEquals(0, result.get().getId());
        assertEquals(1, result.get().getTraineeId());
        assertEquals(1, result.get().getTrainerId());
        assertEquals(1, result.get().getTrainingTypeId());
    }
}
