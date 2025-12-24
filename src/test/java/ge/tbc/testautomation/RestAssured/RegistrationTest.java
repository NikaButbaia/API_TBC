package ge.tbc.testautomation.RestAssured;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class RegistrationTest {
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://35.205.47.8";
        RestAssured.basePath = "/api";
    }
    @Test
    void registrationsPostTest() {
        String token =
                given()
                        .contentType(ContentType.JSON)
                        .body("""
                {
                  "email": "admin@admin.com",
                  "password": "Admin123!"
                }
                """)
                        .when()
                        .post("/Auth/login")
                        .then()
                        .statusCode(200)
                        .extract()
                        .path("token");

        String body = """
    {
      "eventId": 6
    }
    """;

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(body)
                .when()
                .post("/Registrations")
                .then()
                .log().all().statusCode(200);

    }
    @Test
    void registrationsDeleteTest() {
        String token =
                given()
                        .contentType(ContentType.JSON)
                        .body("""
                {
                  "email": "admin@admin.com",
                  "password": "Admin123!"
                }
                """)
                        .when()
                        .post("/Auth/login")
                        .then()
                        .statusCode(200)
                        .extract()
                        .path("token");

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/Registrations/3")
                .then()
                .log().all().statusCode(204);
    }
}
