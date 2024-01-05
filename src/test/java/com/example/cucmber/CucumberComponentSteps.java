package com.example.cucmber;

import com.example.rest.RestController;
import com.example.rest.TrainerRestController;
import com.example.rest.request.*;
import com.example.service.TraineeService;
import com.example.service.TrainerService;
import com.example.service.TrainingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;


@WebMvcTest(controllers = {RestController.class, TrainerRestController.class, TrainerRestController.class})
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class CucumberComponentSteps {

    /** @noinspection SpringJavaInjectionPointsAutowiringInspection*/
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TraineeService traineeService;

    @MockBean
    private TrainerService trainerService;

    @MockBean
    private TrainingService trainingService;

    /** @noinspection SpringJavaInjectionPointsAutowiringInspection*/
    @Autowired
    private ObjectMapper objectMapper;

}
