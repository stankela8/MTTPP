package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class APITest {

    @Test
    public void testGetPost() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        //GET zahtjev
        Response response =
                given()
                        .when()
                        .get("/posts/1")
                        .then()
                        .statusCode(200)  //
                        .body("id", equalTo(1))  //
                        .extract().response();


        System.out.println("Response Body: " + response.asString());


        int userId = response.jsonPath().getInt("userId");
        Assert.assertEquals(userId, 1, "❌ userId nije 1!");
    }
    @Test
    public void testCreatePost() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";


        String requestBody = "{\n" +
                "  \"title\": \"Test Naslov\",\n" +
                "  \"body\": \"Ovo je testni post.\",\n" +
                "  \"userId\": 1\n" +
                "}";


        Response response =
                given()
                        .header("Content-Type", "application/json")
                        .body(requestBody)
                        .when()
                        .post("/posts")
                        .then()
                        .statusCode(201)
                        .body("title", equalTo("Test Naslov"))
                        .extract().response();

        System.out.println("Response Body: " + response.asString());


        String responseBody = response.jsonPath().getString("body");
        Assert.assertEquals(responseBody, "Ovo je testni post.", "❌ Tekst nije isti!");
    }
    @Test
    public void testUpdatePost() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        String updatedBody = "{\n" +
                "  \"title\": \"Ažurirani Naslov\",\n" +
                "  \"body\": \"Ovo je ažurirani sadržaj.\",\n" +
                "  \"userId\": 1\n" +
                "}";

        Response response =
                given()
                        .header("Content-Type", "application/json")
                        .body(updatedBody)
                        .when()
                        .put("/posts/1")
                        .then()
                        .statusCode(200)
                        .body("title", equalTo("Ažurirani Naslov"))
                        .extract().response();

        System.out.println("Response Body: " + response.asString());


        String responseBody = response.jsonPath().getString("body");
        Assert.assertEquals(responseBody, "Ovo je ažurirani sadržaj.", "❌ Ažuriranje neuspješno!");
    }
    @Test
    public void testDeletePost() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        given()
                .when()
                .delete("/posts/1")
                .then()
                .statusCode(200);

        System.out.println("✅ Post je uspješno obrisan.");
    }

}
