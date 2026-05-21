package de.schulung.quarkus;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity(name = "Customer") // Name im JPQL
@Table(name = "CUSTOMERS") // Name der Tabelle in der DB
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private UUID uuid;
  @NotNull
  @Size(min = 3, max = 100)
  private String name;
  // @JsonProperty("birth_date")
  @NotNull
  @Adult
  @Column(name = "DAY_OF_BIRTH")
  private LocalDate birthdate;
  @CustomerState
  private String state;

}
