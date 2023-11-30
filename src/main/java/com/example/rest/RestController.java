package com.example.rest;

import com.example.entity.TrainingType;
import com.example.rest.request.TrainingRequest;
import com.example.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
@Api(value = "Main API", description = "Operations related to Trainee, Trainer, and Training")
public class RestController {
    @Autowired
    private TraineeService traineeService;

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private TrainingService trainingService;

    @Autowired
    private TrainingTypeService trainingTypeService;

    // 3. Login (GET method)
    @ApiOperation(value = "Login", response = String.class)
    @GetMapping("/login")
    public ResponseEntity<String> login(
            @ApiParam(value = "Username", required = true) @RequestParam String username,
            @ApiParam(value = "Password", required = true) @RequestParam String password) {
        try {
            traineeService.authenticateTrainee(username, password);
        } catch (SecurityException e) {
            trainerService.authenticateTrainer(username, password);
        }
        return ResponseEntity.ok("Login successful");
    }

    // 4. Change Login (PUT method)
    @ApiOperation(value = "Change Login", response = String.class)
    @PutMapping("/change-login")
    public ResponseEntity<String> changeLogin(
            @ApiParam(value = "Username", required = true) @RequestParam String username,
            @ApiParam(value = "Old Password", required = true) @RequestParam String oldPassword,
            @ApiParam(value = "New Password", required = true) @RequestParam String newPassword) {
        int id = traineeService.getTraineeByUsername(username, oldPassword).get().getId();
        try {
            traineeService.changeTraineePassword(id, newPassword, username, oldPassword);
        } catch (SecurityException e) {
            trainerService.changeTrainerPassword(id, newPassword, username, oldPassword);
        }
        return ResponseEntity.ok("Password changed successfully");
    }

    //14. Add Training (POST method)
    @ApiOperation(value = "Add Training", response = String.class)
    @PostMapping("/add-training")
    public ResponseEntity<String> addTraining(
            @ApiParam(value = "Training Request", required = true) @Valid @RequestBody TrainingRequest request) {
        trainingService.create(request);
        return ResponseEntity.ok("Training added successfully");
    }

    //17. Get Training types (GET method)
    @ApiOperation(value = "Get Training types", response = TrainingType.class, responseContainer = "List")
    @GetMapping("/training-types")
    public ResponseEntity<List<TrainingType>> getTrainingTypes() {
        return ResponseEntity.ok(trainingTypeService.list());
    }
}