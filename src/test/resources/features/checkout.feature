Feature: Cart

  Scenario: Visitors should be able to order a product
    Given A product has been added to my shopping cart
    And I am on the checkout page
    When I enter my shipping details as
      | email                 | firstName | lastName | company | streetAddress1 | streetAddress2 | streetAddress3 | city        | postalCode | country        | phoneNumber | shippingMethod |
      | rhinoceros@domain.com | Gish      | Knows    |         | 1 Western Esp  |                |                | Southampton | SO14 7SJ   | United Kingdom | 0234 67890  | Fixed          |
    And I review, confirm and place the order
    Then the order is successful