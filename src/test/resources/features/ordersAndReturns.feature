Feature: Orders and Returns

  Scenario: Users can find dedicated information on their orders and returns
    Given I log in
    And I place an order
    And I am on the orders and returns page
    When I view my order
    Then My order is correct