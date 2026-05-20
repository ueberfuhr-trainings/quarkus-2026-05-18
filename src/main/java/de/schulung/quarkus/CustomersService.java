package de.schulung.quarkus;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@ApplicationScoped
public class CustomersService {

  private final Map<UUID, Customer> customers = new ConcurrentHashMap<>();

  public Stream<Customer> getCustomers() {
    return customers
      .values()
      .stream();
  }

  public Stream<Customer> getCustomersByState(String state) {
    return getCustomers()
      .filter(customer -> customer.getState().equals(state));
  }

  public Optional<Customer> getCustomerById(UUID uuid) {
    return Optional
      .ofNullable(customers.get(uuid));
  }

  public void createCustomer(Customer customer) {
    customer.setUuid(UUID.randomUUID());
    customers.put(customer.getUuid(), customer);
  }


}
