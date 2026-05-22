package de.schulung.quarkus.domain;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@ApplicationScoped
@RequiredArgsConstructor
public class CustomersService {

  private final CustomersSink sink;

  public Stream<Customer> getCustomers() {
    return sink.findAll();
  }

  public Stream<Customer> getCustomersByState(CustomerState state) {
    return sink.findAllByState(state);
  }

  public Optional<Customer> getCustomerById(@NotNull UUID uuid) {
    return sink.findById(uuid);
  }

  public void createCustomer(@Valid @NotNull Customer customer) {
    sink.create(customer);
  }


}
