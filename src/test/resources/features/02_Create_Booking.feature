Feature: Creating Booking Feature File

  @NAGP
  Scenario: Post Request to create a booking
    Given user validates the token is generated
    When user sends a POST request to create a booking
    And user should get the booking id
    And user validate the response generated