Feature: Find driver by his firstName
  Scenario: As a user
    When I provide a valid firstName
    Then the driver with the firstName should be returned