package com.example;

import com.example.model.Trainer;
import com.example.repo.TrainerRepository;
import com.example.service.TrainerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainerServiceTest {

    @Mock
    private Trainer trainer;
    @Mock
    private TrainerRepository repository;

    @InjectMocks
    private TrainerService service;

    @Test
    public void testGetId() {
        int expectedInt = 1;
        Map<Integer, Trainer> list = new HashMap<>();
        list.put(1, trainer);
        when(repository.findAll()).thenReturn(list);
        when(trainer.getId()).thenReturn(expectedInt);
        Trainer actual = service.list().get(1L);

        assertEquals(expectedInt, actual.getId());
    }

    @Test
    public void testGetSpecializationId() {
        int expectedInt = 1;
        Map<Integer, Trainer> list = new HashMap<>();
        list.put(1, trainer);
        when(repository.findAll()).thenReturn(list);
        when(trainer.getSpecializationId()).thenReturn(expectedInt);
        Trainer actual = service.list().get(1L);

        assertEquals(expectedInt, actual.getSpecializationId());
    }

    @Test
    public void testGetUserId() {
        int expectedInt = 1;
        Map<Integer, Trainer> list = new HashMap<>();
        list.put(1, trainer);
        when(repository.findAll()).thenReturn(list);
        when(trainer.getUserId()).thenReturn(expectedInt);
        Trainer actual = service.list().get(1L);

        assertEquals(expectedInt, actual.getUserId());
    }
}
