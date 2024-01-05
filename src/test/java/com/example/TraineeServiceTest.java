package com.example;

import com.example.entity.Trainee;
import com.example.entity.Training;
import com.example.repo.TraineeRepository;
import com.example.repo.UserRepository;
import com.example.service.TraineeService;
import io.prometheus.client.Counter;
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
public class TraineeServiceTest {

    @Mock
    private TraineeRepository repository;

    @Mock
    private UserRepository userRepository;


    @InjectMocks
    private TraineeService service;

    @Test
    public void testGetId() {
        int expectedId = 1;
        Trainee trainee = new Trainee();
        trainee.setId(expectedId);

        when(repository.get(expectedId)).thenReturn(Optional.of(trainee));
        int actualId = service.get(expectedId).get().getId();

        assertEquals(expectedId, actualId);
    }

    @Test
    public void testGetDateOfBirth() {
        Date expectedDate = new Date(100, 10, 11); // Adjust year format (e.g., 2000 to 100)
        Trainee trainee = new Trainee();
        trainee.setDateOfBirth(expectedDate);

        when(repository.get(1)).thenReturn(Optional.of(trainee));
        Date actualDate = service.get(1).get().getDateOfBirth();

        assertEquals(expectedDate, actualDate);
    }

    @Test
    public void testGetAddress() {
        String expectedAddr = "Backer St. 221b";
        Trainee trainee = new Trainee();
        trainee.setAddress(expectedAddr);

        when(repository.get(1)).thenReturn(Optional.of(trainee));
        String actualAddr = service.get(1).get().getAddress();

        assertEquals(expectedAddr, actualAddr);
    }

    @Test
    public void testGetUserId() {
        int expectedUserId = 3;
        Trainee trainee = new Trainee();
        trainee.setUserId(expectedUserId);

        when(repository.get(1)).thenReturn(Optional.of(trainee));
        int actualUserId = service.get(1).get().getUserId();

        assertEquals(expectedUserId, actualUserId);
    }

    @Test
    public void testGetTraineeByUsername() {
        String username = "testUser";
        String password = "testPassword";

        Trainee expectedTrainee = new Trainee();

        when(repository.getTraineeByUsername(username)).thenReturn(Optional.of(expectedTrainee));

        Optional<Trainee> actualTrainee = service.getTraineeByUsername(username);

        assertEquals(expectedTrainee, actualTrainee.orElse(null));
    }

    @Test
    public void testDeleteTrainee() {
        int traineeId = 1;
        String username = "username";
        String password = "password";

        Trainee expectedTrainee = new Trainee();
        expectedTrainee.setId(1);

        when(repository.get(1)).thenReturn(Optional.of(expectedTrainee));
        service.delete(traineeId);

        verify(repository).delete(traineeId);
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
