Feature: Advanced Search

  This part of the application allows visitors to search for a product

  Scenario: Search for a product using a product name
    Given I am on the advanced search page
    When I search for the product with
      | productName |
      | bag         |
    Then I am redirected to the advanced search result's page
    And all the product's names contain the input

  Scenario: Search for a product using a SKU value
    Given I am on the advanced search page
    When I search for the product with
      | sku |
      | B   |
    Then I am redirected to the advanced search result's page
    And the products contain the sku value