package de.schulung.quarkus.persistence.panache;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CustomersRepository
  implements PanacheRepositoryBase<CustomerEntity, UUID> {

  public List<CustomerEntity> findAllByState(char state) {
    return list("state", state);
  }

}
