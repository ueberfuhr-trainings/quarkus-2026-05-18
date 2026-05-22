package de.schulung.quarkus.domain;

import de.schulung.quarkus.shared.interceptors.LogPerformance;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
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
  private final Event<Object> eventPublisher;

  public Stream<Customer> getCustomers() {
    return sink.findAll();
  }

  public Stream<Customer> getCustomersByState(CustomerState state) {
    return sink.findAllByState(state);
  }

  public Optional<Customer> getCustomerById(@NotNull UUID uuid) {
    return sink.findById(uuid);
  }

  @LogPerformance
  public void createCustomer(@Valid @NotNull Customer customer) {
    sink.create(customer);
    eventPublisher
      .select(CustomerCreatedEvent.class)
      .fire(new CustomerCreatedEvent(customer));
  }


}
