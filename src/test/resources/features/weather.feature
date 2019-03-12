Feature: Weather App Acceptance Tests
  Background:
    Given User launches weather forecast site

  Scenario Outline: 5 day weather forecast
    When User type city name <city>
    Then User should see 5 day forecast

    Examples:
      | city      |
      | Edinburgh |
      | Glasgow   |

  Scenario Outline: 5 day weather forecast with Hourly forecast
    When User type city name <city>
    And User chose a day <day>
    Then User should see hourly weather forecast for selected <day>

    Examples:
      | city      | day  |
      | Edinburgh | 1  |
      | Glasgow   | 2  |

  Scenario Outline:  Minimum and maximum temperatures
    When User type city name <city>
    And User chose a day <day>
    Then User should see correct summarised minimum temp and maximum temperatures for selected <day> and hour <hour>

    Examples:
      | city      | day  |
      | Edinburgh | 1    |
      | Glasgow   | 2    |
