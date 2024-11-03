import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonPlaceholderApiTest {

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void testUserPostsAndCreatePost() {
        final int oKStatusCode = 200;
        // Get all users and select a random user
        Response users = given()
                .get("/users")
                .then()
                .statusCode(oKStatusCode)
                .extract()
                .response();
        List<Integer> usersIds = users.jsonPath().getList("id");

        // Get a random user
        Random randomValue = new Random();
        int randUserIndex = randomValue.nextInt(usersIds.size());
        int randUserId = usersIds.get(randUserIndex);
        System.out.println("Random user id is: " + randUserId);


        String email = users.jsonPath().getString("[" + randUserIndex + "].email");
        System.out.println("Randomly selected user Email is: " + email);

        // Get randomly selected user posts
        Response posts = given()
                .get("/posts?userId=" + randUserId)
                .then()
                .statusCode(oKStatusCode)
                .extract()
                .response();

        List<Integer> getAllPostsIds = posts.jsonPath().getList("id");

        for (int postId : getAllPostsIds) {
            assertTrue(postId >= 1 && postId <= 100,
                    "User post ids should be in [1, 100] range");
        }

        System.out.println("All post ids are between [1, 100] range.");

        // Create a new post for the user with a non-empty title and body
        String title = "Non Empty Title";
        String body = "Non Empty body for new Post";

        final int createdStatusCode = 201;
        Response createPostResponse = given()
                .header("Content-Type", "application/json")
                .body("{ \"userId\": " + randUserId + ", \"title\": \"" + title + "\", \"body\": \"" + body + "\" }")
                .post("/posts")
                .then()
                .statusCode(createdStatusCode)
                .extract()
                .response();

        int newUserId = createPostResponse.jsonPath().getInt("userId");
        System.out.println("User Id for created post is: " + newUserId);
        String newTitle = createPostResponse.jsonPath().getString("title");
        System.out.println("Title for created post is: " + newTitle);

        String newBody = createPostResponse.jsonPath().getString("body");
        System.out.println("Body for created post is: " + newBody);


        assertEquals(randUserId, newUserId, "Wrong userID is created");
        assertEquals(title, newTitle, "Wrong title is created");
        assertEquals(body, newBody, "Wrong body is created");
    }
}
