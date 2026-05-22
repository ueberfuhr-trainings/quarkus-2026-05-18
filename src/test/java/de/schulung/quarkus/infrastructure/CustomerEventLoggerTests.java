package de.schulung.quarkus.infrastructure;

import de.schulung.quarkus.testing.OutputCaptureExtension;
import de.schulung.quarkus.testing.OutputCaptureExtension.CapturedOutput;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
@TestTransaction
@ExtendWith(OutputCaptureExtension.class)
class CustomerEventLoggerTests {

  // Testkonzept:
  // - wenn ein Kunde angelegt wird
  // - dann erfolgt ein Logging mit "created new customer" und UUID (case insensitive)

  // per HTTP oder direkt an CustomerService
  // hier: per HTTP (BlackBox-Ansatz) bevorzugt

  @Test
  void when_create_customer_then_log_event(CapturedOutput output) {
    var response = given()
      .contentType(ContentType.JSON)
      .body("""
            {
              "name": "Tom Mayer",
              "birthdate": "2001-04-23",
              "state": "active"
            }
        """)
      .accept(ContentType.JSON)
      .when()
      .post("/customers");
    response
      .then()
      .statusCode(201)
      .header("Location", is(notNullValue()));
    var uuid = response.body().jsonPath().getString("uuid");

    assertThat(output)
      .containsPattern(String.format("(?i).*Customer created.*%s.*", uuid));
  }

}
