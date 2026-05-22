package de.schulung.quarkus.persistence.panache;

import de.schulung.quarkus.domain.Customer;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CustomersRepository
  implements PanacheRepositoryBase<Customer, UUID> {

  public List<Customer> findAllByState(String state) {
    return list("state", state);
  }

}
