Feature: Product

  Scenario: Visitors should be able to add a product to their cart
    Given I am on the category page
    When I select a product
    And add the product to my cart
    Then The product is added to my cart

  Scenario: Users should be able to add a product to their wishlist
    Given I log in
    And I am on the category page
    When I select a product
    And add the product to my wish list
    Then The product is added to my wish list

  Scenario: Visitors should be able to add a review to a product
    Given I am on the category page
    When I select a product
    And add a review to the product
    Then The review has been posted