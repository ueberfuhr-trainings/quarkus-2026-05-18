package de.schulung.quarkus;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class Customer {

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private UUID uuid;
  @NotNull
  @Size(min = 3, max = 100)
  private String name;
  // @JsonProperty("birth_date")
  @NotNull
  private LocalDate birthdate;
  @CustomerState
  private String state;

}
