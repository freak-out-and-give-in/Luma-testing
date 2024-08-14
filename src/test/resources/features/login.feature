Feature: Login

  This part of the application allows users to log in

  Rule: Login parameters must be filled in

    #010
    Scenario: User should not be able to log in with no fields filled in
      Given I am on the login page
      When I click the login button
      Then I am not logged in

    # 011
    Scenario: User should be able to log in with the fields filled in with valid data
      Given I already have an account
      And I am on the login page
      When I login with the parameters
        | email            | password      |
        | darcyW@gmail.com | g7vEm$gui5TaR |
      Then I am logged in