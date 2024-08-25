Feature: Deletion of the created booking.

  @NAGP
  Scenario: DELETE Request to delete the created booking
    Given user validates the token is generated
    When user sends a DELETE request to delete the created booking
    Then user validates the booking should be deleted successfully