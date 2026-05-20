package de.schulung.quarkus;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.util.UUID;
import java.util.stream.Stream;

@Path("/customers")
public class CustomersResource {

  private final CustomersService customersService = new CustomersService();

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Stream<Customer> getCustomers(@QueryParam("state") String stateFilter) {
    return
      null == stateFilter
        ? customersService.getCustomers()
        : customersService.getCustomersByState(stateFilter);
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response createCustomer(Customer customer, UriInfo uriInfo) {
    customersService.createCustomer(customer);
    var location = uriInfo
      .getAbsolutePathBuilder()
      .path(customer.getUuid().toString())
      .build();
    return Response
      .created(location)
      .entity(customer)
      .build();
  }

  @GET
  @Path("/{uuid}")
  @Produces(MediaType.APPLICATION_JSON)
  public Customer getCustomerById(@PathParam("uuid") UUID uuid) {
    return customersService
      .getCustomerById(uuid)
      .orElseThrow(NotFoundException::new);
  }


}
