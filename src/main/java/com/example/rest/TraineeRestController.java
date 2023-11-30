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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api/trainee")
@Api(value = "Trainee API", description = "Operations related to Trainee")
public class TraineeRestController {

    @Autowired
    private TraineeService traineeService;

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Trainee Registration", response = Map.class)
    @PostMapping("/registration")
    public ResponseEntity<Map<String, String>> traineeRegistration(@Valid @RequestBody TraineeRegistrationRequest request) {
        // Validate required fields
        if (!StringUtils.hasText(request.getFirstName()) || !StringUtils.hasText(request.getLastName())) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "First Name and Last Name are required"));
        }

        User user = new User(request.getFirstName(), request.getLastName(), true); // Assuming isActive is set to true
        userService.create(user);


        Trainee trainee = new Trainee();
        trainee.setDateOfBirth(request.getDateOfBirth());
        trainee.setAddress(request.getAddress());
        trainee.setUserId(user.getId());
        traineeService.create(trainee);

        Map<String, String> response = new HashMap<>();
        response.put("Username", user.getUserName());
        response.put("Password", user.getPassword());

        return ResponseEntity.ok(response);
    }

    // 5. Get Trainee Profile (GET method)
    @ApiOperation(value = "Get Trainee Profile", response = TraineeProfileResponse.class)
    @GetMapping("/profile")
    public ResponseEntity<TraineeProfileResponse> getTraineeProfile(
            @ApiParam(value = "Username", required = true) @RequestParam String username) {
        Trainee trainee = traineeService.getTraineeByUsername(username, "").get();
        TraineeProfileResponse response = new TraineeProfileResponse(trainee);
        return ResponseEntity.ok(response);
    }

    // 6. Update Trainee Profile (PUT method)
    @ApiOperation(value = "Update Trainee Profile", response = TraineeProfileResponse.class)
    @PutMapping("/profile")
    public ResponseEntity<TraineeProfileResponse> updateTraineeProfile(
            @ApiParam(value = "Username", required = true) @RequestParam String username,
            @Valid @RequestBody TraineeUpdateRequest request) {
        Trainee updatedTrainee = traineeService.update(request);
        TraineeProfileResponse response = new TraineeProfileResponse(updatedTrainee);
        return ResponseEntity.ok(response);
    }

    // 7. Delete Trainee Profile (DELETE method)
    @ApiOperation(value = "Delete Trainee Profile")
    @DeleteMapping("/profile")
    public ResponseEntity<String> deleteTraineeProfile(
            @ApiParam(value = "Username", required = true) @RequestParam String username) {
        int id = traineeService.getTraineeByUsername(username, "").get().getId();
        traineeService.delete(id, username, "");

        return ResponseEntity.ok("Trainee profile deleted successfully");
    }

    // 10. Get not assigned on trainee active trainers. (GET method)
    @ApiOperation(value = "Get not assigned on trainee active trainers", response = TrainerProfileResponse.class, responseContainer = "List")
    @GetMapping("/not-assigned-trainers")
    public ResponseEntity<List<TrainerProfileResponse>> getNotAssignedActiveTrainersForTrainee(
            @ApiParam(value = "Username", required = true) @RequestParam String username) {
        List<Trainer> listOfTrainers = trainerService.getNotAssignedActiveTrainersForTrainee(username);
        List<TrainerProfileResponse> responseList = new ArrayList<>();
        for (Trainer trainer : listOfTrainers) {
            TrainerProfileResponse response = new TrainerProfileResponse(trainer);
            responseList.add(response);
        }
        return ResponseEntity.ok(responseList);
    }

    // 11. Update Trainee's Trainer List (PUT method)
    @ApiOperation(value = "Update Trainee's Trainer List", response = TrainerProfileResponse.class, responseContainer = "List")
    @PutMapping("/update-trainers")
    public ResponseEntity<List<TrainerProfileResponse>> updateTrainers(
            @ApiParam(value = "Username", required = true) @RequestParam String username,
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
    @ApiOperation(value = "Get Trainee Trainings List", response = TrainingResponse.class, responseContainer = "List")
    @GetMapping("/trainings")
    public ResponseEntity<List<TrainingResponse>> getTraineeTrainings(
            @ApiParam(value = "Training Get Request", required = true) @Valid @RequestBody TrainingGetRequest request) {
        return ResponseEntity.ok(traineeService.getTraineeTrainings(request));
    }

    //15. Activate/De-Activate Trainee (PATCH method)
    @ApiOperation(value = "Activate/De-Activate Trainee")
    @PatchMapping("/change-status")
    private ResponseEntity<String> changeStatus(
            @ApiParam(value = "Username", required = true) @RequestParam String username,
            @ApiParam(value = "Status", required = true) @RequestParam Boolean status) {
        traineeService.changeStatus(username, status);
        return ResponseEntity.ok("Status changed successfully");
    }
}