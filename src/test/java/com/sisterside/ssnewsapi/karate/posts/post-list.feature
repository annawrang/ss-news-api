Feature: get list of posts

  Scenario:

    Given path '/posts'
    When method get
    Then status 200