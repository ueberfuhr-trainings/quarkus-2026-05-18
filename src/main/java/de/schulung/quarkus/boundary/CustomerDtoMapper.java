package de.schulung.quarkus.boundary;

import de.schulung.quarkus.domain.Customer;
import de.schulung.quarkus.domain.CustomerState;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface CustomerDtoMapper {

  Customer map(CustomerDto source);

  CustomerDto map(Customer source);

  default String mapState(CustomerState source) {
    return null == source ? null : switch (source) {
      case ACTIVE -> "active";
      case LOCKED -> "locked";
      case DISABLED -> "disabled";
    };
  }

  default CustomerState mapState(String source) {
    return null == source ? null : switch (source) {
      case "active" -> CustomerState.ACTIVE;
      case "locked" -> CustomerState.LOCKED;
      case "disabled" -> CustomerState.DISABLED;
      default -> throw new IllegalArgumentException("Unknown state " + source);
    };
  }

}
