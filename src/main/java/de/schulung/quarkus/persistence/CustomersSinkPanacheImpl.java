package de.schulung.quarkus.persistence;

import de.schulung.quarkus.domain.Customer;
import de.schulung.quarkus.domain.CustomersSink;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@ApplicationScoped
@RequiredArgsConstructor
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
