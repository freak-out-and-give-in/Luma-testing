Feature: Search Terms

  This part of the application allows users to search for products using pre-defined search terms

  # 012
  Scenario: Visitor should be able to click a pre-defined search term to search
    Given I am on the search terms page
    When I click the search term
      | searchTerm |
      | women      |
      | yoga       |
    Then I am redirected to it's search result page