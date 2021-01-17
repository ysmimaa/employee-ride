Feature: Find all drivers
  Scenario: Display all drivers when using findAllDrivers service
    Given Service that return all drivers
    When Calling the findAllDrivers service
    Then All drivers should be displayed