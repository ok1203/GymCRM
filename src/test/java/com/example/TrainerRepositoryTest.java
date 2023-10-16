package com.example;

import com.example.model.Trainer;
import com.example.repo.TrainerRepository;
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
public class TrainerRepositoryTest {
    @InjectMocks
    private TrainerRepository trainerRepository;

    @Mock
    private StorageComponent storageComponent;

    @Test
    public void testFindAll() {
        Map<Integer, Trainer> trainerMap = new HashMap<>();
        Trainer trainer1 = new Trainer(1, 1, 1);
        trainerMap.put(1, trainer1);

        when(storageComponent.getTrainerMap()).thenReturn(trainerMap);

        Map<Integer, Trainer> result = trainerRepository.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(trainer1, result.get(1L));
    }

    @Test
    public void testCreate() {
        Trainer trainer = new Trainer(1, 1, 1);

        Trainer result = trainerRepository.create(trainer);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(1, result.getSpecializationId());
        assertEquals(1, result.getUserId());
    }

    @Test
    public void testGet() {
        Trainer trainer = new Trainer(1, 1, 1);

        when(storageComponent.getTrainer(1)).thenReturn(Optional.of(trainer));

        Optional<Trainer> result = trainerRepository.get(1);

        assertNotNull(result);
        assertEquals(1, result.get().getId());
        assertEquals(1, result.get().getSpecializationId());
        assertEquals(1, result.get().getUserId());
    }

    @Test
    public void testUpdate() {
        Trainer trainer = new Trainer(1, 1, 1);

        when(storageComponent.trainerUpdate(any(Trainer.class))).thenReturn(trainer);

        Trainer result = trainerRepository.update(trainer);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(1, result.getSpecializationId());
        assertEquals(1, result.getUserId());
    }
}
