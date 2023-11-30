package com.example;

import com.example.entity.Trainee;
import com.example.entity.Training;
import com.example.repo.TraineeRepository;
import com.example.service.TraineeService;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TraineeServiceTest {

    @Mock
    private TraineeRepository repository;

    @InjectMocks
    private TraineeService service;

    @Test
    public void testGetId() throws IOException, ParseException {
        int expectedId = 1;
        Trainee trainee = new Trainee();
        trainee.setId(expectedId);

        when(repository.get(expectedId, "username", "password")).thenReturn(Optional.of(trainee));
        int actualId = service.get(expectedId, "username", "password").get().getId();

        assertEquals(expectedId, actualId);
    }

    @Test
    public void testGetDateOfBirth() throws IOException, ParseException {
        Date expectedDate = new Date(100, 10, 11); // Adjust year format (e.g., 2000 to 100)
        Trainee trainee = new Trainee();
        trainee.setDateOfBirth(expectedDate);

        when(repository.get(1, "username", "password")).thenReturn(Optional.of(trainee));
        Date actualDate = service.get(1, "username", "password").get().getDateOfBirth();

        assertEquals(expectedDate, actualDate);
    }

    @Test
    public void testGetAddress() throws IOException, ParseException {
        String expectedAddr = "Backer St. 221b";
        Trainee trainee = new Trainee();
        trainee.setAddress(expectedAddr);

        when(repository.get(1, "username", "password")).thenReturn(Optional.of(trainee));
        String actualAddr = service.get(1, "username", "password").get().getAddress();

        assertEquals(expectedAddr, actualAddr);
    }

    @Test
    public void testGetUserId() throws IOException, ParseException {
        int expectedUserId = 3;
        Trainee trainee = new Trainee();
        trainee.setUserId(expectedUserId);

        when(repository.get(1, "username", "password")).thenReturn(Optional.of(trainee));
        int actualUserId = service.get(1, "username", "password").get().getUserId();

        assertEquals(expectedUserId, actualUserId);
    }

    @Test
    public void testGetTraineeByUsername() throws IOException, ParseException {
        String username = "testUser";
        String password = "testPassword";

        Trainee expectedTrainee = new Trainee();

        when(repository.getTraineeByUsername(username, password)).thenReturn(Optional.of(expectedTrainee));

        Optional<Trainee> actualTrainee = service.getTraineeByUsername(username, password);

        assertEquals(expectedTrainee, actualTrainee.orElse(null));
    }

    @Test
    public void testDeleteTrainee() throws IOException, ParseException {
        int traineeId = 1;
        String username = "username";
        String password = "password";

        service.delete(traineeId, username, password);

        verify(repository).delete(traineeId, username, password);
    }

//    @Test
//    public void testGetTraineeTrainings() throws IOException, ParseException {
//        int traineeId = 1;
//        String username = "username";
//        String password = "password";
//
//        List<Training> expectedTrainings = new ArrayList<>();
//
//        when(repository.getTraineeTrainings(traineeId)).thenReturn(expectedTrainings);
//
//        List<Training> actualTrainings = service.getTraineeTrainings(traineeId);
//
//        assertEquals(expectedTrainings, actualTrainings);
//    }
}
