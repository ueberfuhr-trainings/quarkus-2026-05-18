package de.schulung.quarkus.persistence;

import de.schulung.quarkus.domain.CustomersSink;
import de.schulung.quarkus.persistence.panache.CustomersSinkPanacheImpl;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@TestProfile(PanachePersistenceProfile.class)
class PanachePersistenceActivationTests {

  @Inject
  CustomersSink customersSink;

  @Test
  void shouldNotInjectPanacheImplementation() {
    assertThat(customersSink)
      .isInstanceOf(CustomersSinkPanacheImpl.class);
  }

}
