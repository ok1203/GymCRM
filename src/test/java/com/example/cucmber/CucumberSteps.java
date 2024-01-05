package com.example.cucmber;

import com.example.rest.request.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class CucumberSteps {

    private int port = 8080;

    private ResponseEntity<String> response;

    private RestClient client = RestClient.create();

    private String token;

    private TraineeRegistrationRequest traineeRegistrationRequest;
    private TrainerRegistrationRequest trainerRegistrationRequest;
    private TraineeUpdateRequest traineeUpdateRequest;
    private TrainerUpdateRequest trainerUpdateRequest;
    private TrainingRequest trainingRequest;

    @Before
    public void createToken() throws JsonProcessingException {
        traineeRegistrationRequest = new TraineeRegistrationRequest();
        traineeRegistrationRequest.setFirstName("Token");
        traineeRegistrationRequest.setLastName("Holder");
        traineeRegistrationRequest.setAddress("Test st. Test");
        traineeRegistrationRequest.setDateOfBirth(new Date());
        ResponseEntity<String> holderResponse = client.post()
                .uri("http://localhost:" + port + "/api/trainee/registration")
                .body(traineeRegistrationRequest)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(holderResponse.getBody());
        token = client.get()
                .uri("http://localhost:" + port + "/api/login?username=" + jsonNode.get("Username").asText()
                        + "&password=" + jsonNode.get("Password").asText())
                .retrieve()
                .toEntity(String.class)
                .getBody();
    }

    @After
    public void deleteToken() {
        client.delete()
                .uri("http://localhost:" + port + "/api/trainee/profile?username=Token.Holder")
                .header("Authorization", "Bearer " + token)
                .retrieve();
    }

    @Given("valid trainee to register")
    public void givenTrainee() {
        traineeRegistrationRequest = new TraineeRegistrationRequest();
        traineeRegistrationRequest.setFirstName("Test");
        traineeRegistrationRequest.setLastName("Trainee");
        traineeRegistrationRequest.setAddress("Test st. Test");
        traineeRegistrationRequest.setDateOfBirth(new Date());
    }

    @Given("valid trainer to register")
    public void givenTrainer() {
        trainerRegistrationRequest = new TrainerRegistrationRequest();
        trainerRegistrationRequest.setFirstName("Test");
        trainerRegistrationRequest.setLastName("Trainer");
        trainerRegistrationRequest.setSpecializationId(1);
    }

    @Given("valid trainee to update")
    public void givenUpdateTrainee() {
        traineeRegistrationRequest = new TraineeRegistrationRequest();
        traineeRegistrationRequest.setFirstName("Test");
        traineeRegistrationRequest.setLastName("Trainee");
        traineeRegistrationRequest.setAddress("Test st. Test");
        traineeRegistrationRequest.setDateOfBirth(new Date());
        traineeUpdateRequest = new TraineeUpdateRequest();
        traineeUpdateRequest.setFirstName("Update");
        traineeUpdateRequest.setLastName("Test");
        traineeUpdateRequest.setUsername("Test.Trainee");
        traineeUpdateRequest.setAddress("Update Test st. Test");
        traineeUpdateRequest.setDateOfBirth(new Date());
        traineeUpdateRequest.setActive(false);
    }

    @Given("valid trainer to update")
    public void givenUpdateTrainer() {
        trainerRegistrationRequest = new TrainerRegistrationRequest();
        trainerRegistrationRequest.setFirstName("Test");
        trainerRegistrationRequest.setLastName("Trainer");
        trainerRegistrationRequest.setSpecializationId(1);
        trainerUpdateRequest = new TrainerUpdateRequest();
        trainerUpdateRequest.setFirstName("Update");
        trainerUpdateRequest.setLastName("Test");
        trainerUpdateRequest.setUsername("Test.Trainer");
        trainerUpdateRequest.setSpecializationId(3);
        trainerUpdateRequest.setActive(false);
    }

    @Given("valid training to create")
    public void givenTraining() {
        trainingRequest = new TrainingRequest();
        trainingRequest.setTrainingName("Swimming");
        trainingRequest.setTraineeName("Test.Trainee");
        trainingRequest.setTrainerName("Test.Trainer");
        trainingRequest.setDate(new Date());
        trainingRequest.setDuration(60);
    }

    @Given("invalid trainee to register")
    public void invalidTraineeToRegister() {
        traineeRegistrationRequest = new TraineeRegistrationRequest();
    }

    @Given("invalid trainer to register")
    public void invalidTrainerToRegister() {
        trainerRegistrationRequest = new TrainerRegistrationRequest();
    }

    @Given("invalid training to create")
    public void invalidTrainingToCreate() {
        trainingRequest = new TrainingRequest();
    }

    @Given("invalid trainee to update")
    public void invalidTraineeToUpdate() {
        traineeRegistrationRequest = new TraineeRegistrationRequest();
        traineeRegistrationRequest.setFirstName("Test");
        traineeRegistrationRequest.setLastName("Trainee");
        traineeRegistrationRequest.setAddress("Test st. Test");
        traineeRegistrationRequest.setDateOfBirth(new Date());
        traineeUpdateRequest = new TraineeUpdateRequest();
    }

    @Given("invalid trainer to update")
    public void invalidTrainerToUpdate() {
        trainerRegistrationRequest = new TrainerRegistrationRequest();
        trainerRegistrationRequest.setFirstName("Test");
        trainerRegistrationRequest.setLastName("Trainer");
        trainerRegistrationRequest.setSpecializationId(1);
        trainerUpdateRequest = new TrainerUpdateRequest();
    }

    @When("endpoint at {string} called with get")
    public void endpointGet(String url) {
        response = client.get()
                .uri("http://localhost:" + port + "/api" + url)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .toEntity(String.class);
    }

    @When("endpoint at {string} called with post")
    public void endpointPost(String url) {
        response = client.post()
                .uri("http://localhost:" + port + "/api" + url)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .toEntity(String.class);
    }

    @When("endpoint called with post with trainee registration request")
    public void endpointPostTrainee() {
        try {
            response = client.post()
                    .uri("http://localhost:" + port + "/api/trainee/registration")
                    .header("Authorization", "Bearer " + token)
                    .body(traineeRegistrationRequest)
                    .contentType(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toEntity(String.class);
            client.delete()
                    .uri("http://localhost:" + port + "/api/trainee/profile?username=Test.Trainee")
                    .header("Authorization", "Bearer " + token)
                    .retrieve();
        } catch (HttpClientErrorException.BadRequest e) {
            response = ResponseEntity.status(500).build();
        }
    }

    @When("endpoint called with post with trainer registration request")
    public void endpointPostTrainer() {
        try {
            response = client.post()
                    .uri("http://localhost:" + port + "/api/trainer/registration")
                    .header("Authorization", "Bearer " + token)
                    .body(trainerRegistrationRequest)
                    .contentType(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toEntity(String.class);
        } catch (HttpClientErrorException.BadRequest e) {
            response = ResponseEntity.status(500).build();
        }
    }

    @When("endpoint called with training creating request")
    public void endpointPostTraining() {
        try {
            response = client.post()
                    .uri("http://localhost:" + port + "/api/add-training")
                    .header("Authorization", "Bearer " + token)
                    .body(trainingRequest)
                    .contentType(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toEntity(String.class);
        } catch (HttpClientErrorException.BadRequest e) {
            response = ResponseEntity.status(500).build();
        }
    }

    @When("endpoint called with put with trainee updating request")
    public void endpointPutTrainee() {
        try {
            client.post()
                    .uri("http://localhost:" + port + "/api/trainee/registration")
                    .header("Authorization", "Bearer " + token)
                    .body(traineeRegistrationRequest)
                    .contentType(MediaType.APPLICATION_JSON)
                    .retrieve();
            response = client.put()
                    .uri("http://localhost:" + port + "/api/trainee/profile")
                    .header("Authorization", "Bearer " + token)
                    .body(traineeUpdateRequest)
                    .contentType(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toEntity(String.class);
            client.delete()
                    .uri("http://localhost:" + port + "/api/trainee/profile?username=Test.Trainee")
                    .header("Authorization", "Bearer " + token)
                    .retrieve();
        } catch (HttpClientErrorException.BadRequest e) {
            response = ResponseEntity.status(500).build();
        }
    }

    @When("endpoint called with put with trainer updating request")
    public void endpointPutTrainer() {
        try {
            client.post()
                    .uri("http://localhost:" + port + "/api/trainer/registration")
                    .header("Authorization", "Bearer " + token)
                    .body(trainerRegistrationRequest)
                    .contentType(MediaType.APPLICATION_JSON)
                    .retrieve();
            response = client.put()
                    .uri("http://localhost:" + port + "/api/trainer/profile")
                    .header("Authorization", "Bearer " + token)
                    .body(trainerUpdateRequest)
                    .contentType(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toEntity(String.class);
        } catch (HttpClientErrorException.BadRequest e) {
            response = ResponseEntity.status(500).build();
        }
    }

    @When("endpoint called with put for updating {string} trainee with {string} trainer")
    public void endpointTraineeTrainerUpdate(String username, String trainer) {
        try {
            client.post()
                    .uri("http://localhost:" + port + "/api/trainee/registration")
                    .header("Authorization", "Bearer " + token)
                    .body(traineeRegistrationRequest)
                    .contentType(MediaType.APPLICATION_JSON)
                    .retrieve();
            client.post()
                    .uri("http://localhost:" + port + "/api/trainer/registration")
                    .header("Authorization", "Bearer " + token)
                    .body(trainerRegistrationRequest)
                    .contentType(MediaType.APPLICATION_JSON)
                    .retrieve();
            response = client.put()
                    .uri("http://localhost:" + port + "/api/trainee/update-trainers?username=" + username)
                    .header("Authorization", "Bearer " + token)
                    .body(List.of(trainer))
                    .contentType(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toEntity(String.class);
            client.delete()
                    .uri("http://localhost:" + port + "/api/trainee/profile?username=Test.Trainee")
                    .header("Authorization", "Bearer " + token)
                    .retrieve();
        } catch (HttpClientErrorException.BadRequest e) {
            response = ResponseEntity.status(500).build();
        }
    }

    @When("endpoint called with patch for changing {string} trainee status")
    public void endpointChangeStatusTrainee(String username) {
        try {
            client.post()
                    .uri("http://localhost:" + port + "/api/trainee/registration")
                    .header("Authorization", "Bearer " + token)
                    .body(traineeRegistrationRequest)
                    .contentType(MediaType.APPLICATION_JSON)
                    .retrieve();
            response = client.patch()
                    .uri("http://localhost:" + port + "/api/trainee/change-status?username=" + username + "&status=false")
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .toEntity(String.class);
            client.delete()
                    .uri("http://localhost:" + port + "/api/trainee/profile?username=Test.Trainee")
                    .header("Authorization", "Bearer " + token)
                    .retrieve();
        } catch (HttpClientErrorException.BadRequest e) {
            response = ResponseEntity.status(500).build();
        }
    }

    @When("endpoint called with patch for changing {string} trainer status")
    public void endpointChangeStatusTrainer(String username) {
        try {
            client.post()
                    .uri("http://localhost:" + port + "/api/trainer/registration")
                    .header("Authorization", "Bearer " + token)
                    .body(trainerRegistrationRequest)
                    .contentType(MediaType.APPLICATION_JSON)
                    .retrieve();
            response = client.patch()
                    .uri("http://localhost:" + port + "/api/trainer/change-status?username=" + username + "&status=false")
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .toEntity(String.class);
        } catch (HttpClientErrorException.BadRequest e) {
            response = ResponseEntity.status(500).build();
        }
    }

    @When("endpoint called with delete {string} trainee")
    public void endpointDeleteTrainee(String username) {
        try {
            client.post()
                    .uri("http://localhost:" + port + "/api/trainee/registration")
                    .header("Authorization", "Bearer " + token)
                    .body(traineeRegistrationRequest)
                    .contentType(MediaType.APPLICATION_JSON)
                    .retrieve();
            response = client.delete()
                    .uri("http://localhost:" + port + "/api/trainee/profile?username=" + username)
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .toEntity(String.class);
        } catch (HttpClientErrorException.BadRequest e) {
            response = ResponseEntity.status(500).build();
        }
    }

    @When("endpoint at {string} called with get without token")
    public void endpointGetNoToken(String url) {
        try {
            response = client.get()
                    .uri("http://localhost:" + port + "/api" + url)
                    .retrieve()
                    .toEntity(String.class);
        } catch (HttpClientErrorException.Forbidden e) {
            response = ResponseEntity.status(403).build();
        }
    }

    @Then("returned status code is {int}")
    public void thenStatusCode(int code) {
        assertThat("status code is " + code, response.getStatusCode().value() == code);
    }

    @Then("response body should contain {string}")
    public void response_body_should_contain(String expectedText) {
        String responseBody = response.getBody(); // Assuming response is a String

        assertTrue(responseBody.contains(expectedText));
    }

}
