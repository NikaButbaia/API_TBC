package ge.tbc.testautomation.RestAssured;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class AuthTests {

@BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://35.205.47.8";
        RestAssured.basePath = "/api";
    }

@Test
public void logInTest() {
    String body = """
                {
                      "email": "33@33",
                      "password": "asdasdasdA1"
                }
                """;
    given()
            .when()
            .body(body)
            .contentType("application/json")
            .post("/auth/login")
            .then()
            .statusCode(200);
    }
@Test
public void registerTest() {
    String body = """
            {
              "email": "TBCIT@gmail.com",
              "password": "WeAreBetterThanBOG",
              "fullName": "zaza pachulia"
            }
            """;
    given()
            .when()
            .header("Authorization", "Bearer bearer")
            .body(body)
            .contentType("application/json")
            .post("/Auth/register")
            .then()
            .statusCode(201);
}
    @Test
    public void getInfoTest_shouldReturn200_whenAuthorized() {
        String token =
                given()
                        .contentType(ContentType.JSON)
                        .body("""
                        {
                          "email": "33@33",
                          "password": "asdasdasdA1"
                        }
                        """)
                        .when()
                        .post("/Auth/login")
                        .then()
                        .statusCode(200)
                        .extract()
                        .path("token");

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/Auth/me")
                .then()
                .statusCode(200);
    }
}
