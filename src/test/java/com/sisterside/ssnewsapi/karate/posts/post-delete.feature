Feature: delete a post for a given #postNumber

  Scenario:

    Given path '/posts/' + "001"
    When method delete
    Then status 204
