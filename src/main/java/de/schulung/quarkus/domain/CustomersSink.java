package de.schulung.quarkus.domain;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface CustomersSink {

  Stream<Customer> findAll();

  default Stream<Customer> findAllByState(String state) {
    return findAll()
      .filter(c -> c.getState().equals(state));
  }

  default Optional<Customer> findById(UUID id) {
    return findAll()
      .filter(c -> c.getUuid().equals(id))
      .findFirst();
  }

  void create(Customer customer);

}
