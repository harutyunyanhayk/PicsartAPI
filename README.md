# PicsartAPI
Project to automate '{JSON} Placeholder'


Functional automation for API testing. Write a test in Java following the REST API: https://jsonplaceholder.typicode.com/
Requirements:
1.	Get a random user (userID) and print out their email address to the console.
2.	Using this userID, get the user’s associated posts and verify they contain valid Post IDs (an integer between 1 and 100).
3.	Create a post using the same userID with a non-empty title and body, and verify the correct response is returned.

Used "io.restassured.RestAssured" and "io.restassured.response.Response" libs to make request-response for comleting requirements.
