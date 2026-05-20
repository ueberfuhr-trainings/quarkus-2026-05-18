package de.schulung.quarkus;

import com.fasterxml.jackson.databind.DeserializationFeature;
import io.quarkus.jackson.ObjectMapperCustomizer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class JacksonConfiguration {

  @Produces
  @Dependent
  public ObjectMapperCustomizer objectMapperCustomizer() {
    return objectMapper -> objectMapper
      .configure(
        DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,
        true
      );
  }


}
