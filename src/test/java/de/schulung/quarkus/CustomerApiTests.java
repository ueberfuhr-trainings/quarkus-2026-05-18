package de.schulung.quarkus;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

@QuarkusTest
@TestTransaction
class CustomerApiTests {

  // GET /customers -> 200 + JSON (Array)

  @Test
  void when_get_customers_then_status_ok() {
    given()
      .accept(ContentType.JSON)
      .when()
      .get("/customers")
      .then()
      .statusCode(200)
      .contentType(ContentType.JSON)
      .body("", is(instanceOf(List.class)));
  }

  @Test
  void given_invalid_state_parameter_when_get_customers_then_status_bad_request() {
    given()
      .accept(ContentType.JSON)
      .queryParam("state", "gelbekatze")
      .when()
      .get("/customers")
      .then()
      .statusCode(400);
  }

  // GET /customers mit XML -> 406

  @Test
  void given_accept_xml_when_get_customers_then_status_not_acceptable() {
    given()
      .accept(ContentType.XML)
      .when()
      .get("/customers")
      .then()
      .statusCode(406);
  }

  @Test
  void when_post_customers_then_status_created_and_json_response() {
    given()
      .contentType(ContentType.JSON)
      .body("""
            {
              "name": "Tom Mayer",
              "birthdate": "2000-05-19",
              "state": "active"
            }
        """)
      .accept(ContentType.JSON)
      .when()
      .post("/customers")
      .then()
      .statusCode(201)
      .contentType(ContentType.JSON)
      .body("name", is(equalTo("Tom Mayer")))
      .body("birthdate", is(equalTo("2000-05-19")))
      .body("state", is(equalTo("active")))
      .body("uuid", is(notNullValue()));
  }

  // Content Negotiation: invalid Accept -> 406, invalid Content-Type -> 415

  @Test
  void when_post_customers_and_invalid_accept_then_status_not_acceptable() {
    given()
      .contentType(ContentType.JSON)
      .body("""
            {
              "name": "Tom Mayer",
              "birthdate": "2020-05-19",
              "state": "active"
            }
        """)
      .accept(ContentType.XML)
      .when()
      .post("/customers")
      .then()
      .statusCode(406);
  }

  @Test
  void when_post_customers_and_invalid_content_type_then_status_unsupported_mediatype() {
    given()
      .contentType(ContentType.XML)
      .body("<customer/>")
      .accept(ContentType.JSON)
      .when()
      .post("/customers")
      .then()
      .statusCode(415);
  }

  // when created, GET /customers should return array with new customer (with UUID)
  @Test
  void given_created_customer_when_get_customers_then_customer_is_in_array() {
    // setup
    var newCustomerUuid = given()
      .contentType(ContentType.JSON)
      .body("""
            {
              "name": "Tom Mayer",
              "birthdate": "2000-05-19",
              "state": "active"
            }
        """)
      .accept(ContentType.JSON)
      .when()
      .post("/customers")
      .then()
      .statusCode(201)
      .contentType(ContentType.JSON)
      .body("uuid", is(notNullValue()))
      .extract().path("uuid");

    // test
    given()
      .accept(ContentType.JSON)
      .when()
      .get("/customers")
      .then()
      .statusCode(200)
      .contentType(ContentType.JSON)
      // see https://github.com/rest-assured/rest-assured/wiki/usage#json-example
      .body("find { it.uuid == '%s' }".formatted(newCustomerUuid), is(notNullValue()));

  }


  // when created, use UUID to request for single customer
  @Test
  void given_created_customer_when_get_customer_by_uuid_then_customer_is_returned() {
    // setup
    var newCustomerUuid = given()
      .contentType(ContentType.JSON)
      .body("""
            {
              "name": "Tom Mayer",
              "birthdate": "2000-05-19",
              "state": "active"
            }
        """)
      .accept(ContentType.JSON)
      .when()
      .post("/customers")
      .then()
      .statusCode(201)
      .contentType(ContentType.JSON)
      .body("uuid", is(notNullValue()))
      .extract().path("uuid");

    // test
    given()
      .accept(ContentType.JSON)
      .pathParam("uuid", newCustomerUuid)
      .when()
      .get("/customers/{uuid}")
      .then()
      .statusCode(200)
      .contentType(ContentType.JSON)
      .body("name", is(equalTo("Tom Mayer")))
      .body("birthdate", is(equalTo("2000-05-19")))
      .body("state", is(equalTo("active")))
      .body("uuid", is(equalTo(newCustomerUuid)));

  }

  // Location-Header exists and contains URL to new resource
  @Test
  void given_created_customer_when_get_customer_by_location_header_then_customer_is_returned() {
    // setup
    var newCustomerLocation = given()
      .contentType(ContentType.JSON)
      .body("""
            {
              "name": "Tom Mayer",
              "birthdate": "2000-05-19",
              "state": "active"
            }
        """)
      .accept(ContentType.JSON)
      .when()
      .post("/customers")
      .then()
      .statusCode(201)
      .header("Location", is(notNullValue()))
      .extract().header("Location");

    // test
    given()
      .accept(ContentType.JSON)
      .baseUri(newCustomerLocation)
      .when()
      .get()
      .then()
      .statusCode(200)
      .contentType(ContentType.JSON)
      .body("name", is(equalTo("Tom Mayer")))
      .body("birthdate", is(equalTo("2000-05-19")))
      .body("state", is(equalTo("active")));

  }

  // when requesting a non-existing customer, 404 is returned
  @Test
  void given_non_existing_customer_when_get_customer_by_uuid_then_not_found() {
    // setup
    // TODO: better is to create the customer and delete it (DELETE necessary)
    var nonExistingCustomerUuid = UUID.randomUUID();

    // test
    given()
      .accept(ContentType.JSON)
      .pathParam("uuid", nonExistingCustomerUuid)
      .when()
      .get("/customers/{uuid}")
      .then()
      .statusCode(404);

  }

  // invalid accept header for single customer request -> 406
  @Test
  void given_created_customer_when_get_customer_by_uuid_with_invalid_accept_then_not_acceptable() {
    // setup
    var newCustomerUuid = given()
      .contentType(ContentType.JSON)
      .body("""
            {
              "name": "Tom Mayer",
              "birthdate": "2000-05-19",
              "state": "active"
            }
        """)
      .accept(ContentType.JSON)
      .when()
      .post("/customers")
      .then()
      .statusCode(201)
      .contentType(ContentType.JSON)
      .body("uuid", is(notNullValue()))
      .extract().path("uuid");

    // test
    given()
      .accept(ContentType.XML)
      .pathParam("uuid", newCustomerUuid)
      .when()
      .get("/customers/{uuid}")
      .then()
      .statusCode(406);

  }

  // GET /customers?state=locked should not contain a customer that was created with state=active
  @Test
  void given_created_customer_with_active_state_when_get_customers_with_locked_state_then_customer_is_not_in_array() {
    // setup
    var newCustomerUuid = given()
      .contentType(ContentType.JSON)
      .body("""
            {
              "name": "Tom Mayer",
              "birthdate": "2000-05-19",
              "state": "active"
            }
        """)
      .accept(ContentType.JSON)
      .when()
      .post("/customers")
      .then()
      .statusCode(201)
      .contentType(ContentType.JSON)
      .body("uuid", is(notNullValue()))
      .extract().path("uuid");

    // test
    given()
      .accept(ContentType.JSON)
      .queryParam("state", "locked")
      .when()
      .get("/customers")
      .then()
      .statusCode(200)
      .contentType(ContentType.JSON)
      // see https://github.com/rest-assured/rest-assured/wiki/usage#json-example
      .body("find { it.uuid == '%s' }".formatted(newCustomerUuid), is(nullValue()));

  }

  static Stream<String> invalidCustomerJsons() {
    return Stream.of(
      // name with more than 100 characters
      """
        {
          "name": "%s",
          "birthdate": "2001-04-23",
          "state": "active"
        }
        """
        .formatted(
          "a".repeat(101)
        ),
      // customer is too young
      """
        {
          "name": "Tom Mayer",
          "birthdate": "%s",
          "state": "active"
        }
        """
        .formatted(
          LocalDate
            .now()
            .minusYears(10)
            .format(DateTimeFormatter.ISO_DATE)
        )
    );
  }

  @ParameterizedTest
  @MethodSource("invalidCustomerJsons")
  @ValueSource(strings = {
    // UUID included
    """
      {
        "uuid": "12345678-1234-1234-1234-123456789012",
        "name": "Tom Mayer",
        "birthdate": "2001-04-23",
        "state": "active"
      }
      """,
    // unknown property
    """
      {
        "name": "Tom Mayer",
        "birthdate": "2001-04-23",
        "state": "active",
        "gelbekatze": "test"
      }
      """,
    // invalid birthdate format
    """
      {
        "name": "Tom Mayer",
        "birthdate": "gelbekatze",
        "state": "active"
      }
      """,
    // missing name
    """
      {
        "birthdate": "2001-04-23",
        "state": "active"
      }
      """,
    // missing birthdate
    """
      {
        "name": "Tom Mayer",
        "state": "active"
      }
      """,
    // name with less than 3 characters
    """
      {
        "name": "T",
        "birthdate": "2001-04-23",
        "state": "active"
      }
      """,
    // invalid state
    """
      {
        "name": "Tom Mayer",
        "birthdate": "2001-04-23",
        "state": "gelbekatze"
      }
      """,

  })
  void given_invalid_customer_when_post_customers_then_bad_request(String body) {
    given()
      .contentType(ContentType.JSON)
      .body(body)
      .accept(ContentType.JSON)
      .when()
      .post("/customers")
      .then()
      .statusCode(400);
  }

}
