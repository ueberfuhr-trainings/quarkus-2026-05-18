package de.schulung.quarkus.domain;

import de.schulung.quarkus.shared.validation.Adult;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class Customer {

  private UUID uuid;
  @NotNull
  @Size(min = 3, max = 100)
  private String name;
  @NotNull
  @Adult
  private LocalDate birthdate;
  @CustomerState
  private String state; // TODO Enum

}
