package de.schulung.quarkus;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
class CustomersServiceTests {

  @Inject
  CustomersService customersService;

  // Idee: ParameterizedTest mit mehreren Customers-Objekten
  @Test
  void given_invalid_customer_when_create_customer_then_validation_exception() {
    final var customer = new Customer();
    // Idee: AssertJ Assertions
    assertThrows(Exception.class, () -> customersService.createCustomer(customer));
  }

}
