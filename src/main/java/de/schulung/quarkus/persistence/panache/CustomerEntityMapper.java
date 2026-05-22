package de.schulung.quarkus.persistence.panache;

import de.schulung.quarkus.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi")
public interface CustomerEntityMapper {

  CustomerEntity map(Customer source);

  Customer map(CustomerEntity source);

  void copy(CustomerEntity entity, @MappingTarget Customer customer);
}
