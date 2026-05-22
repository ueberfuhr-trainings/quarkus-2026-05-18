package de.schulung.quarkus.persistence.panache;

import de.schulung.quarkus.domain.Customer;
import de.schulung.quarkus.domain.CustomerState;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi")
public interface CustomerEntityMapper {

  CustomerEntity map(Customer source);

  Customer map(CustomerEntity source);

  void copy(CustomerEntity entity, @MappingTarget Customer customer);

  default String mapState(CustomerState source) {
    return null == source ? null : switch (source) {
      case ACTIVE -> "A";
      case LOCKED -> "L";
      case DISABLED -> "D";
    };
  }

  default CustomerState mapState(String source) {
    return null == source ? null : switch (source) {
      case "A" -> CustomerState.ACTIVE;
      case "L" -> CustomerState.LOCKED;
      case "D" -> CustomerState.DISABLED;
      default -> throw new IllegalArgumentException("Unknown state " + source);
    };
  }

}
