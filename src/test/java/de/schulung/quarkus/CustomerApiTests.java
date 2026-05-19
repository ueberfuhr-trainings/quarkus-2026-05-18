package de.schulung.quarkus;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
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
              "birthdate": "2020-05-19",
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
      .body("birthdate", is(equalTo("2020-05-19")))
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

  // TODO: when created, GET /customers should return array with new customer (with UUID)

  // TODO: when created, use UUID to request for single customer
  // TODO: Location-Header exists and contains URL to new resource
  // TODO: when requesting a non-existing customer, 404 is returned
  // TODO: invalid accept header for single customer request -> 406
  // TODO: when creating a customer, a UUID must not be sent with the request body -> 400
  // TODO: GET /customers?state=locked should not contain a customer that was created with state=active


}
