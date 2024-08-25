Feature: Fetching created Booking Feature File

  @NAGP
  Scenario: GET Request to fetch created booking
    Given user validates the token is generated
    When user performs GET request to fetch created booking ids
    Then user validates the booking created in previous step in the response

  @NAGP
  Scenario: Get Request to fetch booking details for particular booking id
    Given user validates the token is generated
    When user performs GET request to fetch booking details for specific booking id
    Then user validates the booking details in the response