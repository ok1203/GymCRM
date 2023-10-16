package com.example;

import com.example.model.Trainee;
import com.example.repo.TraineeRepository;
import com.example.storage.StorageComponent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    private StorageComponent storageComponent;


    @Test
    public void testFindAll() throws IOException, ParseException, org.json.simple.parser.ParseException {
        Map<Integer, Trainee> traineeMap = new HashMap<>();
        Trainee trainee1 = new Trainee(1, new Date(2000, 11, 11), "Address1", 1);
        traineeMap.put(1, trainee1);

        when(storageComponent.getTraineeMap()).thenReturn(traineeMap);

        Map<Integer, Trainee> result = traineeRepository.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(trainee1, result.get(1L));
    }

    @Test
    public void testCreate() {
        Trainee trainee = new Trainee(1, new Date(2000, 11, 11), "Address1", 1);

        when(storageComponent.createTrainee(any(Trainee.class))).thenReturn(trainee);

        Trainee result = traineeRepository.create(trainee);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(new Date(2000, 11, 11), result.getDateOfBirth());
        assertEquals("Address1", result.getAddress());
        assertEquals(1, result.getUserId());
    }

    @Test
    public void testGet() {
        Trainee trainee = new Trainee(1, new Date(2000, 11, 11), "Address1", 1);

        when(storageComponent.getTrainee(1)).thenReturn(Optional.of(trainee));

        Optional<Trainee> result = traineeRepository.get(1);

        assertNotNull(result);
        assertEquals(1, result.get().getId());
        assertEquals(new Date(2000, 11, 11), result.get().getDateOfBirth());
        assertEquals("Address1", result.get().getAddress());
        assertEquals(1, result.get().getUserId());
    }

    @Test
    public void testUpdate() {
        Trainee trainee = new Trainee(1, new Date(2000, 11, 11), "Address1", 1);

        when(storageComponent.traineeUpdate(any(Trainee.class))).thenReturn(trainee);

        Trainee result = traineeRepository.update(trainee);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(new Date(2000, 11, 11), result.getDateOfBirth());
        assertEquals("Address1", result.getAddress());
        assertEquals(1, result.getUserId());
    }

    @Test
    public void testDelete() {
        traineeRepository.delete(1);

        verify(storageComponent).deleteTrainee(1);
    }
}
