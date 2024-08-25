Feature: Updating the created booking.

  @NAGP
  Scenario: PUT Request to update the created booking
    Given user validates the token is generated
    When user sends a PUT request to update the created booking
    Then user validates the booking should be updated successfully

  @NAGP
  Scenario: Patch request to partially update the created booking
    Given user validates the token is generated
    When user sends a Patch request to partially update the created booking
    Then user validates the booking should be updated successfully