
  Feature: Compare

    Scenario: Visitors should be able to add products to their comparison list
      Given I add 2 products to my comparison list
      When I go to my comparison page
      Then both the products are there

      Scenario: Visitors should be able to remove products from their comparison list
        Given I add 3 products to my comparison list
        When I go to my comparison page
        And remove a product from my comparison list
        Then the product is removed from my comparison list