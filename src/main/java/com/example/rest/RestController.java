package com.example.rest;

import com.example.entity.TrainingType;
import com.example.rest.request.TrainingRequest;
import com.example.service.*;
import com.example.service.security.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {
    @Autowired
    private TraineeService traineeService;

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private TrainingService trainingService;

    @Autowired
    private TrainingTypeService trainingTypeService;

    @Autowired
    private AuthenticationService authenticationService;

    // 3. Login (GET method)
    @GetMapping("/login")
    public ResponseEntity<String> login(
            @RequestParam String username,
            @RequestParam String password) {
        return ResponseEntity.ok(authenticationService.authenticate(username, password));
    }

    // 4. Change Login (PUT method)
    @PutMapping("/change-login")
    public ResponseEntity<String> changeLogin(
            @RequestParam String username,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        int id = traineeService.getTraineeByUsername(username, oldPassword).get().getId();
        try {
            traineeService.changeTraineePassword(id, newPassword, username, oldPassword);
        } catch (SecurityException e) {
            trainerService.changeTrainerPassword(id, newPassword, username, oldPassword);
        }
        return ResponseEntity.ok("Password changed successfully");
    }

    //14. Add Training (POST method)
    @PostMapping("/add-training")
    public ResponseEntity<String> addTraining(
            @Valid @RequestBody TrainingRequest request) {
        trainingService.create(request);
        return ResponseEntity.ok("Training added successfully");
    }

    //17. Get Training types (GET method)
    @GetMapping("/training-types")
    public ResponseEntity<List<TrainingType>> getTrainingTypes() {
        return ResponseEntity.ok(trainingTypeService.list());
    }
}