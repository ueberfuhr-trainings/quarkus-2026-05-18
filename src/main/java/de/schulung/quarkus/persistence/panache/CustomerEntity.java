package de.schulung.quarkus.persistence.panache;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity(name = "Customer") // Name im JPQL
@Table(name = "CUSTOMERS") // Name der Tabelle in der DB
public class CustomerEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID uuid;
  private String name;
  @Column(name = "DAY_OF_BIRTH")
  private LocalDate birthdate;
  private String state;

}
