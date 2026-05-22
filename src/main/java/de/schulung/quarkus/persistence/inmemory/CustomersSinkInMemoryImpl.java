package de.schulung.quarkus.persistence.inmemory;

import de.schulung.quarkus.domain.Customer;
import de.schulung.quarkus.domain.CustomersSink;
import io.quarkus.arc.DefaultBean;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Typed;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Typed(CustomersSink.class)
@ApplicationScoped
@DefaultBean
public class CustomersSinkInMemoryImpl
  implements CustomersSink {

  private final Map<UUID, Customer> customers = new ConcurrentHashMap<>();

  @Override
  public Stream<Customer> findAll() {
    return customers
      .values()
      .stream();
  }

  @Override
  public Optional<Customer> findById(UUID id) {
    return Optional
      .ofNullable(customers.get(id));
  }

  @Override
  public void create(Customer customer) {
    customer.setUuid(UUID.randomUUID());
    customers.put(customer.getUuid(), customer);

  }

}
