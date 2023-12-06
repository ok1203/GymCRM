package com.example;

import com.example.entity.User;
import com.example.repo.UserRepository;
import com.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private User user;
    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    @Test
    public void testGetId() {
        int expectedInt = 1;
        List<User> userList = new ArrayList<>();
        userList.add(user);
        when(repository.findAll()).thenReturn(userList);
        when(user.getId()).thenReturn(expectedInt);
        User actual = service.list().get(0);

        assertEquals(expectedInt, actual.getId());
    }

    @Test
    public void testGetFirstName() {
        String expectedStr = "John";
        List <User> userList = new ArrayList<>();
        userList.add(user);
        when(repository.findAll()).thenReturn(userList);
        when(user.getFirstName()).thenReturn(expectedStr);
        User actual = service.list().get(0);

        assertEquals(expectedStr, actual.getFirstName());
    }

    @Test
    public void testGetLastName() {
        String expectedStr = "Johnson";
        List <User> userList = new ArrayList<>();
        userList.add(user);
        when(repository.findAll()).thenReturn(userList);
        when(user.getLastName()).thenReturn(expectedStr);
        User actual = service.list().get(0);

        assertEquals(expectedStr, actual.getLastName());
    }

    @Test
    public void testGetUsername() {
        String expectedStr = "john.johnson";
        List <User> userList = new ArrayList<>();
        userList.add(user);
        when(repository.findAll()).thenReturn(userList);
        when(user.getUserName()).thenReturn(expectedStr);
        User actual = service.list().get(0);

        assertEquals(expectedStr, actual.getUserName());
    }

    @Test
    public void testGetPassword() {
        String expectedStr = "password";
        List <User> userList = new ArrayList<>();
        userList.add(user);
        when(repository.findAll()).thenReturn(userList);
        when(user.getPassword()).thenReturn(expectedStr);
        User actual = service.list().get(0);

        assertEquals(expectedStr, actual.getPassword());
    }

    @Test
    public void testIsIsActive() {
        Boolean expectedBool = true;
        List <User> userList = new ArrayList<>();
        userList.add(user);
        when(repository.findAll()).thenReturn(userList);
        when(user.isActive()).thenReturn(expectedBool);
        User actual = service.list().get(0);

        assertTrue(actual.isActive());
    }
}
