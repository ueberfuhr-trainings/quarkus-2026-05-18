package de.schulung.quarkus.persistence.panache;

import de.schulung.quarkus.domain.Customer;
import de.schulung.quarkus.domain.CustomersSink;
import io.quarkus.arc.properties.UnlessBuildProperty;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Typed;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

/*
 * Only allow injection by type with CustomersSink.
 * "@Inject CustomersSinkPanacheImpl sink" will fail!
 */
@Typed(CustomersSink.class)
@ApplicationScoped
@RequiredArgsConstructor
@UnlessBuildProperty(
  name = "application.persistence.jpa.enabled",
  stringValue = "false",
  enableIfMissing = true
)
public class CustomersSinkPanacheImpl
  implements CustomersSink {

  private final CustomersRepository customersRepository;

  @Override
  public Stream<Customer> findAll() {
    return customersRepository
      .findAll()
      .stream();
  }

  @Override
  public Stream<Customer> findAllByState(String state) {
    return customersRepository
      .findAllByState(state)
      .stream();
  }

  @Override
  public Optional<Customer> findById(UUID id) {
    return customersRepository
      .findByIdOptional(id);
  }

  @Transactional
  @Override
  public void create(Customer customer) {
    customersRepository.persist(customer);
  }

}
