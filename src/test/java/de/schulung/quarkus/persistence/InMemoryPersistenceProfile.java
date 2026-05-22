package de.schulung.quarkus.persistence;

import io.quarkus.test.junit.QuarkusTestProfile;

import java.util.Map;

public class InMemoryPersistenceProfile
  implements QuarkusTestProfile {

  @Override
  public Map<String, String> getConfigOverrides() {
    return Map.of(
      "application.persistence.jpa.enabled", "false"
    );
  }

}
