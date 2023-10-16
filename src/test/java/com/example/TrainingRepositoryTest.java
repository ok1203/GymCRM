package com.example;

import com.example.model.Training;
import com.example.repo.TrainingRepository;
import com.example.storage.StorageComponent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainingRepositoryTest {

    @InjectMocks
    private TrainingRepository trainingRepository;

    @Mock
    private StorageComponent storageComponent;

    @Test
    public void testFindAll() {
        Map<Integer, Training> trainingMap = new HashMap<>();
        Training training1 = new Training(1, 1, 1, "Test Training", 1, null, 60);
        trainingMap.put(1, training1);

        when(storageComponent.getTrainingMap()).thenReturn(trainingMap);

        Map<Integer, Training> result = trainingRepository.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(training1, result.get(1L));
    }

    @Test
    public void testCreate() {
        Training training = new Training(1, 1, 1, "Test Training", 1, null, 60);

        when(storageComponent.createTraining(any(Training.class))).thenReturn(training);

        Training result = trainingRepository.create(training);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(1, result.getTraineeId());
        assertEquals(1, result.getTrainerId());
        assertEquals(1, result.getTrainingTypeId());
    }

    @Test
    public void testGet() {
        Training training = new Training(1, 1, 1, "Test Training", 1, null, 60);

        when(storageComponent.getTraining(1)).thenReturn(Optional.of(training));

        Optional<Training> result = trainingRepository.get(1);

        assertNotNull(result);
        assertEquals(1, result.get().getId());
        assertEquals(1, result.get().getTraineeId());
        assertEquals(1, result.get().getTrainerId());
        assertEquals(1, result.get().getTrainingTypeId());
    }
}
