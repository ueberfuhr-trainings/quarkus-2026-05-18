package de.schulung.quarkus.domain;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@QuarkusTest
@TestTransaction
class CustomersServiceTests {

  @Inject
  CustomersService customersService;

  static Stream<Arguments> invalidCustomers() {
    return Stream.of(
      Arguments.of(
        "empty customer",
        new Customer()
      ),
      Arguments.of(
        "null customer",
        null
      ),
      Arguments.of(
        "customer without birthdate",
        new Customer()
          .setName("Tom Mayer")
          .setState("active")
      )
    );
  }

  @ParameterizedTest(name = "[{index}] {0}")
  @MethodSource("invalidCustomers")
  void given_invalid_customer_when_create_customer_then_validation_exception(
    String description,
    Customer customer
  ) {
    assertThatThrownBy(() -> customersService.createCustomer(customer))
      .isInstanceOf(ValidationException.class);
  }

  @Test
  void given_null_id_when_get_customer_by_id_then_validation_exception() {
    assertThatThrownBy(() -> customersService.getCustomerById(null))
      .isInstanceOf(ValidationException.class);
  }

}
