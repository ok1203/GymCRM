package com.example.rest;

import com.example.entity.Trainer;
import com.example.entity.User;
import com.example.rest.request.TrainerRegistrationRequest;
import com.example.rest.request.TrainerUpdateRequest;
import com.example.rest.request.TrainingGetRequest;
import com.example.rest.response.TrainerProfileResponse;
import com.example.rest.response.TrainingResponse;
import com.example.service.TrainerService;
import com.example.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api/trainer")
@Api(value = "Trainer API", description = "Operations related to Trainer")
public class TrainerRestController {

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private UserService userService;

    //2. Trainer Registration (POST method)
    @ApiOperation(value = "Trainer Registration", response = Map.class)
    @PostMapping("/registration")
    public ResponseEntity<Map<String, String>> trainerRegistration(@Valid @RequestBody TrainerRegistrationRequest request) {
        // Validate required fields
        if (!StringUtils.hasText(request.getFirstName()) || !StringUtils.hasText(request.getLastName())
                || request.getSpecializationId() <= 0) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "First Name, Last Name, and Specialization are required"));
        }

        User user = new User(request.getFirstName(), request.getLastName(), true);
        userService.create(user);


        Trainer trainer = new Trainer();
        trainer.setSpecializationId(request.getSpecializationId());
        trainer.setUserId(user.getId());
        trainerService.create(trainer);

        Map<String, String> response = new HashMap<>();
        response.put("Username", user.getUserName());
        response.put("Password", user.getPassword());

        return ResponseEntity.ok(response);
    }

    // 8. Get Trainer Profile (GET method)
    @ApiOperation(value = "Get Trainer Profile", response = TrainerProfileResponse.class)
    @GetMapping("/profile")
    public ResponseEntity<TrainerProfileResponse> getTrainerProfile(
            @ApiParam(value = "Username", required = true) @RequestParam String username) {
        Trainer trainer = trainerService.getTrainerByUsername(username, "").get();
        TrainerProfileResponse response = new TrainerProfileResponse(trainer);
        return ResponseEntity.ok(response);
    }

    // 9. Update Trainer Profile (PUT method)
    @ApiOperation(value = "Update Trainer Profile", response = TrainerProfileResponse.class)
    @PutMapping("/profile")
    public ResponseEntity<TrainerProfileResponse> updateTrainerProfile(
            @ApiParam(value = "Username", required = true) @RequestParam String username,
            @Valid @RequestBody TrainerUpdateRequest request) {
        Trainer updatedTrainer = trainerService.update(request);
        TrainerProfileResponse response = new TrainerProfileResponse(updatedTrainer);
        return ResponseEntity.ok(response);
    }

    //13. Get Trainer Trainings List (GET method)
    @ApiOperation(value = "Get Trainer Trainings List", response = TrainingResponse.class, responseContainer = "List")
    @GetMapping("/trainings")
    public ResponseEntity<List<TrainingResponse>> getTrainerTrainings(
            @ApiParam(value = "Training Get Request", required = true) @Valid @RequestBody TrainingGetRequest request) {
        return ResponseEntity.ok(trainerService.getTrainerTrainings(request));
    }

    //16. Activate/De-Activate Trainer (PATCH method)
    @ApiOperation(value = "Activate/De-Activate Trainer")
    @PatchMapping("/change-status")
    private ResponseEntity<String> changeStatus(
            @ApiParam(value = "Username", required = true) @RequestParam String username,
            @ApiParam(value = "Status", required = true) @RequestParam Boolean status) {
        trainerService.changeStatus(username, status);
        return ResponseEntity.ok("Status changed successfully");
    }
}