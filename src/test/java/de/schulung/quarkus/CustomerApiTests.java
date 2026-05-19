package de.schulung.quarkus;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@QuarkusTest
class CustomerApiTests {

  // GET /customers -> 200 + JSON (Array)

  @Test
  void when_get_customers_then_status_ok() {
    given()
      .when()
      .get("/customers")
      .then()
      .statusCode(200)
      .contentType(ContentType.JSON)
      .body("", is(instanceOf(List.class)));
  }


}
