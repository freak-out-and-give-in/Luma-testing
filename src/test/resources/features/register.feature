Feature: Register

  This part of the application allows visitors to become users

  Rule: Registration parameters must be filled in

    # 001
    Scenario: Visitors should not be able to create an account with no fields filled in
      Given I am on the create account page
      When I click the create account button
      Then no account is created

    # 003
    Scenario: Visitors should be able to create an account with the fields filled in with valid data
      Given I am on the create account page
      When I register with the parameters
        | firstName | lastName | email            | password      | confirmPassword |
        | Darcy     | Wretzky  | darcyW@gmail.com | g7vEm$gui5TaR | g7vEm$gui5TaR   |
      Then my account is created