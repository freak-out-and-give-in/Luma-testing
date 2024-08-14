Feature: Category

  Scenario: Visitors can sort products by position
    Given I am on a category page
    When I sort by "PRODUCT_NAME"
    Then The products are sorted by "PRODUCT_NAME"

  Scenario: Visitors can sort products by price
    Given I am on a category page
    When I sort by "PRICE"
    Then The products are sorted by "PRICE"