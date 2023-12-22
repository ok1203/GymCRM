package com.example.rest;

import com.example.entity.TrainingType;
import com.example.rest.request.TrainingRequest;
import com.example.rest.response.UserDetailsDTO;
import com.example.service.*;
import com.example.service.security.AuthenticationService;
import com.example.service.security.CustomUserDetailsService;
import com.example.service.security.TokenBlacklistService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.web.servlet.mvc.condition.RequestConditionHolder;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {
    @Autowired
    private UserService userService;

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

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    // 3. Login (GET method)
    @GetMapping("/login")
    public ResponseEntity<String> login(
            @RequestParam String username,
            @RequestParam String password) {
        return ResponseEntity.ok(authenticationService.authenticate(username, password));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        tokenBlacklistService.addToBlacklist(token);
        return ResponseEntity.ok().build();
    }

    // 4. Change Login (PUT method)
    @PutMapping("/change-login")
    public ResponseEntity<Void> changeLogin(
            @RequestParam String username,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        authenticationService.authenticate(username, oldPassword);
        userService.changeUserPassword(userService.getByUsername(username).get().getId(), newPassword);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user-details/{username}")
    public UserDetailsDTO getUserDetails(@PathVariable String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        return UserDetailsDTO.fromUserDetails(userDetails);
    }

    //14. Add Training (POST method)
    @PostMapping("/add-training")
    public ResponseEntity<Void> addTraining(
            @Valid @RequestBody TrainingRequest request) {
        trainingService.create(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete-training/{id}")
    public ResponseEntity<Void> deleteTraining(@PathVariable int id) {
        trainingService.delete(id);
        return ResponseEntity.ok().build();
    }

    //17. Get Training types (GET method)
    @GetMapping("/training-types")
    public ResponseEntity<List<TrainingType>> getTrainingTypes() {
        return ResponseEntity.ok(trainingTypeService.list());
    }
}