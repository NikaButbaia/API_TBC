package ge.tbc.testautomation.RestAssured;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class EventsTest {
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://35.205.47.8";
        RestAssured.basePath = "/api";
    }
    @Test
    void getEventsTest() {
        given()
                .when()
                .contentType("application/json")
                .get("/events")
                .then()
                .statusCode(200);
    }
    @Test
    void apiEventsTest() {
        String body = """
            {
              "title": "Project Presentation",
                    "description": "event to present sprint result",
                    "eventTypeId": 2,
                    "startDateTime": "2025-12-26T19:00:18.875Z",
                    "endDateTime": "2025-12-26T21:00:18.875Z",
                    "location": "Stamba",
                    "capacity": 40,
                    "imageUrl": "string",
                    "tagIds": [
                      1,2,3
                    ]
            }
            """;
        given()
                .when().body(body)
                .contentType("application/json")
                .post("/events")
                .then()
                .statusCode(200);
    }
    @Test
    void apiEventsIdTest() {
        given()
                .when()
                .header("id","2")
                .contentType("application/json")
                .get("/events")
                .then()
                .statusCode(200);
    }
    @Test
    void apiEventsIdPutTest() {
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
      "id": 1,
      "title": "Trys",
      "description": "event to present sprint result",
      "eventTypeId": 1,
      "startDateTime": "2025-12-26T19:00:18.875Z",
      "endDateTime": "2025-12-26T21:00:18.875Z",
      "location": "vake",
      "capacity": 40,
      "imageUrl": "string",
      "tagIds": [1,2,3]
    }
    """;
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(body)
                .when()
                .put("/Events/1")
                .then()
                .log().all()
                .statusCode(200);
    }
    @Test
    void apiEventsIdDeleteTest() {
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
                .delete("/Events/1")
                .then()
                .log().all()
                .statusCode(204);
    }
}
