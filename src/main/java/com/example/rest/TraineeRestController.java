package com.example.rest;

import com.example.entity.Trainee;
import com.example.entity.Trainer;
import com.example.entity.User;
import com.example.rest.request.TraineeRegistrationRequest;
import com.example.rest.request.TraineeUpdateRequest;
import com.example.rest.request.TrainingGetRequest;
import com.example.rest.response.TraineeProfileResponse;
import com.example.rest.response.TrainerProfileResponse;
import com.example.rest.response.TrainingResponse;
import com.example.service.TraineeService;
import com.example.service.TrainerService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.*;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api/trainee")
public class TraineeRestController {

    @Autowired
    private TraineeService traineeService;

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private UserService userService;

    //1. Trainee Registration (POST method)
    @PostMapping("/registration")
    public ResponseEntity<Map<String, String>> traineeRegistration(@Valid @RequestBody TraineeRegistrationRequest request) {
        Trainee trainee = traineeService.create(request);

        Map<String, String> response = new HashMap<>();
        response.put("Username", userService.get(trainee.getUserId()).get().getUserName());
        response.put("Password", traineeService.getTemporaryPassword(trainee.getUserId()));

        return ResponseEntity.ok(response);
    }

    // 5. Get Trainee Profile (GET method)
    @GetMapping("/profile")
    public ResponseEntity<TraineeProfileResponse> getTraineeProfile(
            @RequestParam String username) {
        Trainee trainee = traineeService.getTraineeByUsername(username, "").get();
        TraineeProfileResponse response = new TraineeProfileResponse(trainee);
        return ResponseEntity.ok(response);
    }

    // 6. Update Trainee Profile (PUT method)
    @PutMapping("/profile")
    public ResponseEntity<TraineeProfileResponse> updateTraineeProfile(
            @RequestParam String username,
            @Valid @RequestBody TraineeUpdateRequest request) {
        Trainee updatedTrainee = traineeService.update(request);
        TraineeProfileResponse response = new TraineeProfileResponse(updatedTrainee);
        return ResponseEntity.ok(response);
    }

    // 7. Delete Trainee Profile (DELETE method)
    @DeleteMapping("/profile")
    public ResponseEntity<String> deleteTraineeProfile(
            @RequestParam String username) {
        int id = traineeService.getTraineeByUsername(username, "").get().getId();
        traineeService.delete(id, username, "");

        return ResponseEntity.ok("Trainee profile deleted successfully");
    }

    // 10. Get not assigned on trainee active trainers. (GET method)
    @GetMapping("/not-assigned-trainers")
    public ResponseEntity<List<TrainerProfileResponse>> getNotAssignedActiveTrainersForTrainee(
            @RequestParam String username) {
        List<Trainer> listOfTrainers = trainerService.getNotAssignedActiveTrainersForTrainee(username);
        List<TrainerProfileResponse> responseList = new ArrayList<>();
        for (Trainer trainer : listOfTrainers) {
            TrainerProfileResponse response = new TrainerProfileResponse(trainer);
            responseList.add(response);
        }
        return ResponseEntity.ok(responseList);
    }

    // 11. Update Trainee's Trainer List (PUT method)
    @PutMapping("/update-trainers")
    public ResponseEntity<List<TrainerProfileResponse>> updateTrainers(
            @RequestParam String username,
            @Valid @RequestBody List<String> listOfTrainersUsername) {
        List<Trainer> listOfTrainers = traineeService.updateTrainers(username, listOfTrainersUsername);
        List<TrainerProfileResponse> responseList = new ArrayList<>();
        for (Trainer trainer : listOfTrainers) {
            TrainerProfileResponse response = new TrainerProfileResponse(trainer);
            responseList.add(response);
        }
        return ResponseEntity.ok(responseList);
    }

    //12. Get Trainee Trainings List (GET method)
    @GetMapping("/trainings")
    public ResponseEntity<List<TrainingResponse>> getTraineeTrainings(
            @Valid @RequestBody TrainingGetRequest request) {
        return ResponseEntity.ok(traineeService.getTraineeTrainings(request));
    }

    //15. Activate/De-Activate Trainee (PATCH method)
    @PatchMapping("/change-status")
    private ResponseEntity<String> changeStatus(
            @RequestParam String username,
            @RequestParam Boolean status) {
        traineeService.changeStatus(username, status);
        return ResponseEntity.ok("Status changed successfully");
    }
}