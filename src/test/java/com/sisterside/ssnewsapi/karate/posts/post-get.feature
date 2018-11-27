Feature: get a post for a given #postNumber

  Scenario:

    Given path '/posts/' + "001"
    When method get
    Then status 200
    And match $ == {postNumber:#notnull}