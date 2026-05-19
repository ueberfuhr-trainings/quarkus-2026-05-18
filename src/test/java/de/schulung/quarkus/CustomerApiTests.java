package de.schulung.quarkus;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class CustomerApiTests {

  // GET /customers -> 200

  @Test
  void when_get_customers_then_status_ok() {
    given()
      .when()
      .get("/customers")
      .then()
      .statusCode(200);
  }


}
