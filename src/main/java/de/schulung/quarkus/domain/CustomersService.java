package de.schulung.quarkus.domain;

import de.schulung.quarkus.persistence.CustomersRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@ApplicationScoped
@RequiredArgsConstructor
public class CustomersService {

  private final CustomersRepository customersRepository;

  public Stream<Customer> getCustomers() {
    return customersRepository
      .findAll()
      .stream();
  }

  public Stream<Customer> getCustomersByState(String state) {
    return customersRepository
      .findAllByState(state)
      .stream();
  }

  public Optional<Customer> getCustomerById(@NotNull UUID uuid) {
    return customersRepository
      .findByIdOptional(uuid);
  }

  @Transactional
  public void createCustomer(@Valid @NotNull Customer customer) {
    customersRepository.persist(customer);
  }


}
