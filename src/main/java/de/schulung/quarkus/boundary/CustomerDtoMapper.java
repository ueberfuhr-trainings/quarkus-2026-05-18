package de.schulung.quarkus.boundary;

import de.schulung.quarkus.domain.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface CustomerDtoMapper {

  Customer map(CustomerDto source);

  CustomerDto map(Customer source);

}
