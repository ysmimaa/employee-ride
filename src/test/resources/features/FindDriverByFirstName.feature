Feature: Find driver by his firstName

  Scenario: When user provide a valid driver's firstName then driver with the firstName provided should be displayed
    Given A valid param like "firstName"
    When calling the findDriverByFirstName service
    Then the driver with the param "firstName" should be returned

  Scenario: When user provide a valid driver's firstName and there is only 1 driver then count of driver should be 1
    Given A valid param like "firstName"
    When calling the findDriverByFirstName service
    Then the count should return 1
