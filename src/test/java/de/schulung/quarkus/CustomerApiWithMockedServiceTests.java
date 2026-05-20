package de.schulung.quarkus;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@QuarkusTest
class CustomerApiWithMockedServiceTests {

  @InjectMock
  CustomersService customersService;

  // when requesting a non-existing customer, 404 is returned
  @Test
  void given_non_existing_customer_when_get_customer_by_uuid_then_not_found() {
    // setup
    var nonExistingCustomerUuid = UUID.randomUUID();
    when(customersService.getCustomerById(nonExistingCustomerUuid))
      .thenReturn(Optional.empty());

    // test
    given()
      .accept(ContentType.JSON)
      .pathParam("uuid", nonExistingCustomerUuid)
      .when()
      .get("/customers/{uuid}")
      .then()
      .statusCode(404);

    verify(customersService)
      .getCustomerById(nonExistingCustomerUuid);

  }

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

    // Mockito.verify(customersService, Mockito.never()).createCustomer(Mockito.any());
    verifyNoInteractions(customersService);

  }

}
