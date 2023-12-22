package com.example.rest;

import com.example.entity.Trainer;
import com.example.rest.request.TrainerRegistrationRequest;
import com.example.rest.request.TrainerUpdateRequest;
import com.example.rest.request.TrainingGetRequest;
import com.example.rest.response.TrainerProfileResponse;
import com.example.rest.response.TrainingResponse;
import com.example.service.TrainerService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api/trainer")
public class TrainerRestController {

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private UserService userService;

    //2. Trainer Registration (POST method)
    @PostMapping("/registration")
    public ResponseEntity<Map<String, String>> trainerRegistration(@Valid @RequestBody TrainerRegistrationRequest request) {
        Trainer trainer = trainerService.create(request);

        Map<String, String> response = new HashMap<>();
        response.put("Username", userService.get(trainer.getUserId()).get().getUserName());
        response.put("Password", trainerService.getTemporaryPassword(trainer.getUserId()));

        return ResponseEntity.ok(response);
    }

    // 8. Get Trainer Profile (GET method)
    @GetMapping("/profile")
    public ResponseEntity<TrainerProfileResponse> getTrainerProfile(
            @RequestParam String username) {
        Trainer trainer = trainerService.getTrainerByUsername(username).get();
        TrainerProfileResponse response = new TrainerProfileResponse(trainer);
        return ResponseEntity.ok(response);
    }

    // 9. Update Trainer Profile (PUT method)
    @PutMapping("/profile")
    public ResponseEntity<TrainerProfileResponse> updateTrainerProfile(
            @Valid @RequestBody TrainerUpdateRequest request) {
        Trainer updatedTrainer = trainerService.update(request);
        TrainerProfileResponse response = new TrainerProfileResponse(updatedTrainer);
        return ResponseEntity.ok(response);
    }

    //13. Get Trainer Trainings List (GET method)
    @GetMapping("/trainings")
    public ResponseEntity<List<TrainingResponse>> getTrainerTrainings(
            @Valid @RequestBody TrainingGetRequest request) {
        return ResponseEntity.ok(trainerService.getTrainerTrainings(request));
    }

    //16. Activate/De-Activate Trainer (PATCH method)
    @PatchMapping("/change-status")
    private ResponseEntity<Void> changeStatus(
            @RequestParam String username,
            @RequestParam Boolean status) {
        trainerService.changeStatus(username, status);
        return ResponseEntity.ok().build();
    }
}