Feature: Calculate the courier cost

  Scenario Outline:
    Given I need to deliver the below packages
    When the weight of the package is "<weight>"
    And the distance to be travelled is "<distance>"
    And the offer code is "<offercode>"
    Then the total cost of delivery is "<totalCost>"

    Examples:
      | weight | distance | offercode | totalCost |
