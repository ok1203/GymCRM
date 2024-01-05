Feature: Rest test
  Scenario: Get training types OK
    When endpoint at "/training-types" called with get
    Then returned status code is 200
    Then response body should contain "Boxing"
    
  Scenario: Post trainee registration
    Given valid trainee to register
    When endpoint called with post with trainee registration request
    Then returned status code is 200
    Then response body should contain "Test.Trainee"
    
  Scenario: Post trainer registration
    Given valid trainer to register
    When endpoint called with post with trainer registration request
    Then returned status code is 200
    Then response body should contain "Test.Trainer"

  Scenario: Post training registration
    Given valid training to create
    When endpoint called with training creating request
    Then returned status code is 200
    Then response body should contain "Swimming"

  Scenario: Put trainee update
    Given valid trainee to update
    When endpoint called with put with trainee updating request
    Then returned status code is 200
  
  Scenario: Put trainer update
    Given valid trainer to update
    When endpoint called with put with trainer updating request
    Then returned status code is 200
    
  Scenario: Put trainee update trainer
    Given valid trainee to register
    Given valid trainer to register
    When endpoint called with put for updating "Test.Trainee" trainee with "Test.Trainer" trainer
    Then returned status code is 200

  Scenario: Patch trainee status
    Given valid trainee to register
    When endpoint called with patch for changing "Test.Trainee" trainee status
    Then returned status code is 200
    
  Scenario: Patch trainer status
    Given valid trainer to register
    When endpoint called with patch for changing "Test.Trainer" trainer status
    Then returned status code is 200
    
  Scenario: Delete trainee
    Given valid trainee to register
    When endpoint called with delete "Test.Trainee" trainee
    Then returned status code is 200
    
  Scenario: Jwt invalid call
    When endpoint at "/training-types" called with get without token
    Then returned status code is 403

  Scenario: Post trainee registration invalid
    Given invalid trainee to register
    When endpoint called with post with trainee registration request
    Then returned status code is 500

  Scenario: Post trainer registration invalid
    Given invalid trainer to register
    When endpoint called with post with trainer registration request
    Then returned status code is 500

  Scenario: Post training registration invalid
    Given invalid training to create
    When endpoint called with training creating request
    Then returned status code is 500

  Scenario: Put trainee update invalid
    Given invalid trainee to update
    When endpoint called with put with trainee updating request
    Then returned status code is 500

  Scenario: Put trainer update invalid
    Given invalid trainer to update
    When endpoint called with put with trainer updating request
    Then returned status code is 500