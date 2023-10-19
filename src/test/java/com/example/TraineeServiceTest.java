package com.example;

import com.example.model.Trainee;
import com.example.repo.TraineeRepository;
import com.example.repo.service.TraineeService;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TraineeServiceTest {

    @Mock
    private Trainee trainee;
    @Mock
    private TraineeRepository repository;

    @InjectMocks
    private TraineeService service;

    @Test
    public void testGetId() throws IOException, ParseException {
        int expectedInt = 1;
        Map<Integer, Trainee> list = new HashMap<>();
        list.put(1, trainee);
        when(repository.findAll()).thenReturn(list);
        when(trainee.getId()).thenReturn(expectedInt);
        Trainee actual = service.list().get(1L);

        assertEquals(expectedInt, actual.getId());
    }

    @Test
    public void testGetDateOfBirth() throws IOException, ParseException {
        Date expectedDate = new Date(2000, 11, 11);
        Map<Integer, Trainee> list = new HashMap<>();
        list.put(1, trainee);
        when(repository.findAll()).thenReturn(list);
        when(trainee.getDateOfBirth()).thenReturn(expectedDate);
        Trainee actual = service.list().get(1L);

        assertEquals(expectedDate, actual.getDateOfBirth());
    }

    @Test
    public void testGetAddress() throws IOException, ParseException {
        String expectedAddr = "Backer St. 221b";
        Map<Integer, Trainee> list = new HashMap<>();
        list.put(1, trainee);
        when(repository.findAll()).thenReturn(list);
        when(trainee.getAddress()).thenReturn(expectedAddr);
        Trainee actual = service.list().get(1L);

        assertEquals(expectedAddr, actual.getAddress());
    }

    @Test
    public void testGetUserId() throws IOException, ParseException {
        int expectedInt = 1;
        Map<Integer, Trainee> list = new HashMap<>();
        list.put(1, trainee);
        when(repository.findAll()).thenReturn(list);
        when(trainee.getUserId()).thenReturn(expectedInt);
        Trainee actual = service.list().get(1L);

        assertEquals(expectedInt, actual.getUserId());
    }
}
