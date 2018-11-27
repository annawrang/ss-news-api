Feature: creates a post

  Scenario:

    Given path '/posts'
    And request read('classpath:karate/posts/json/POST-posts.json')
    When method post
    Then status 201