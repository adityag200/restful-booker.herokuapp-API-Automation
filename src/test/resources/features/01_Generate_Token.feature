Feature: Creating Booking Feature File

  @NAGP
  Scenario: Generate the token to be used in future
    When I generate a token
    Then I should get a token