package com.example.rest;

import com.example.entity.Trainee;
import com.example.entity.Trainer;
import com.example.entity.User;
import com.example.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {
    @Autowired
    private TraineeService traineeService;

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private UserService userService;

    @Autowired
    private TrainingService trainingService;

    @Autowired
    private TrainingTypeService trainingTypeService;

    @PostMapping("/trainee/registration")
    public ResponseEntity<Map<String, String>> traineeRegistration(@RequestBody TraineeRegistrationRequest request) {
        // Validate required fields
        if (!StringUtils.hasText(request.getFirstName()) || !StringUtils.hasText(request.getLastName())) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "First Name and Last Name are required"));
        }

        User user = new User(request.getFirstName(), request.getLastName(), true); // Assuming isActive is set to true

        Trainee trainee = new Trainee();
        trainee.setDateOfBirth(request.getDateOfBirth());
        trainee.setAddress(request.getAddress());
        trainee.setUserId(user.getId());

        Trainee createdTrainee = traineeService.create(trainee);

        Map<String, String> response = new HashMap<>();
        response.put("Username", createdTrainee.getGym_user().getUserName());
        response.put("Password", createdTrainee.getGym_user().getPassword());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/trainer/registration")
    public ResponseEntity<Map<String, String>> trainerRegistration(@RequestBody TrainerRegistrationRequest request) {
        // Validate required fields
        if (!StringUtils.hasText(request.getFirstName()) || !StringUtils.hasText(request.getLastName())
                || request.getSpecializationId() <= 0) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "First Name, Last Name, and Specialization are required"));
        }

        User user = new User(request.getFirstName(), request.getLastName(), true);

        Trainer trainer = new Trainer(request.getSpecializationId(), user.getId());

        Trainer createdTrainer = trainerService.create(trainer);

        Map<String, String> response = new HashMap<>();
        response.put("Username", createdTrainer.getGym_user().getUserName());
        response.put("Password", createdTrainer.getGym_user().getPassword());

        return ResponseEntity.ok(response);
    }

    // 3. Login (GET method)
    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        try {
            traineeService.authenticateTrainee(username, password);
        }
        catch(SecurityException e) {
            trainerService.authenticateTrainer(username, password);
        }

        return ResponseEntity.ok("Login successful");
    }

    // 4. Change Login (PUT method)
    @PutMapping("/change-login")
    public ResponseEntity<String> changeLogin(@RequestParam String username, @RequestParam String oldPassword, @RequestParam String newPassword) {
        int id = traineeService.getTraineeByUsername(username, oldPassword).get().getId();
        try {
            traineeService.changeTraineePassword(id, newPassword, username, oldPassword);
        }
        catch(SecurityException e) {
            trainerService.changeTrainerPassword(id, newPassword, username, oldPassword);
        }
        return ResponseEntity.ok("Password changed successfully");
    }

    // 5. Get Trainee Profile (GET method)
    @GetMapping("/trainee/profile")
    public ResponseEntity<TraineeProfileResponse> getTraineeProfile(@RequestParam String username) {
        Trainee trainee = traineeService.getTraineeByUsername(username, "").get();

        TraineeProfileResponse response = new TraineeProfileResponse(trainee);

        return ResponseEntity.ok(response);
    }

    // 6. Update Trainee Profile (PUT method)
    @PutMapping("/trainee/profile")
    public ResponseEntity<TraineeProfileResponse> updateTraineeProfile(@RequestParam String username, @RequestBody TraineeRegistrationRequest request) {
        Trainee updatedTrainee = traineeService.update(convertRequestToTrainee(request), username, "");
        TraineeProfileResponse response = new TraineeProfileResponse(updatedTrainee);

        return ResponseEntity.ok(response);
    }

    // Helper method to convert TraineeRegistrationRequest to Trainee entity
    private Trainee convertRequestToTrainee(TraineeRegistrationRequest request) {
        Trainee trainee = new Trainee();
        User user = new User(request.getFirstName(), request.getLastName(), true);
        trainee.setDateOfBirth(request.getDateOfBirth());
        trainee.setAddress(request.getAddress());
        trainee.setUserId(user.getId());
        return trainee;
    }

    // 7. Delete Trainee Profile (DELETE method)
    @DeleteMapping("/trainee/profile")
    public ResponseEntity<String> deleteTraineeProfile(@RequestParam String username) {
        int id = traineeService.getTraineeByUsername(username, "").get().getId();
        traineeService.delete(id, username, "");

        return ResponseEntity.ok("Trainee profile deleted successfully");
    }

    // 8. Get Trainer Profile (GET method)
    @GetMapping("/trainer/profile")
    public ResponseEntity<TrainerProfileResponse> getTrainerProfile(@RequestParam String username) {
        Trainer trainer = trainerService.getTrainerByUsername(username, "").get();

        TrainerProfileResponse response = new TrainerProfileResponse(trainer);

        return ResponseEntity.ok(response);
    }

    // 9. Update Trainer Profile (PUT method)
    @PutMapping("/trainer/profile")
    public ResponseEntity<TrainerProfileResponse> updateTrainerProfile(@RequestParam String username, @RequestBody TrainerRegistrationRequest request) {
        Trainer updatedTrainer = trainerService.update(convertRequestToTrainer(request), username, "");

        TrainerProfileResponse response = new TrainerProfileResponse(updatedTrainer);

        return ResponseEntity.ok(response);
    }

    private Trainer convertRequestToTrainer(TrainerRegistrationRequest request) {
        Trainer trainer = new Trainer();
        User user = new User(request.getFirstName(), request.getLastName(), true);
        trainer.setUserId(user.getId());
        return trainer;
    }



}

