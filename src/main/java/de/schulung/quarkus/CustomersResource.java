package de.schulung.quarkus;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.Collection;
import java.util.List;

@Path("/customers")
public class CustomersResource {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Collection<?> getCustomers() {
    return List.of();
  }

}
