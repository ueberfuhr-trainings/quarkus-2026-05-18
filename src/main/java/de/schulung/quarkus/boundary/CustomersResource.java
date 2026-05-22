package de.schulung.quarkus.boundary;

import de.schulung.quarkus.domain.CustomersService;
import jakarta.validation.Valid;
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
import lombok.RequiredArgsConstructor;

import java.util.UUID;
import java.util.stream.Stream;

@Path("/customers")
@RequiredArgsConstructor
public class CustomersResource {

  private final CustomersService customersService;
  private final CustomerDtoMapper mapper;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Stream<CustomerDto> getCustomers(
    @QueryParam("state")
    @ValidCustomerState
    String stateFilter
  ) {
    return
      (
        null == stateFilter
          ? customersService.getCustomers()
          : customersService.getCustomersByState(mapper.mapState(stateFilter))
      )
        .map(mapper::map);
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response createCustomer(@Valid CustomerDto customerDto, UriInfo uriInfo) {
    final var customer = mapper.map(customerDto);
    customersService.createCustomer(customer);
    final var responseCustomerDto = mapper.map(customer);
    var location = uriInfo
      .getAbsolutePathBuilder()
      .path(responseCustomerDto.getUuid().toString())
      .build();
    return Response
      .created(location)
      .entity(responseCustomerDto)
      .build();
  }

  @GET
  @Path("/{uuid}")
  @Produces(MediaType.APPLICATION_JSON)
  public CustomerDto getCustomerById(@PathParam("uuid") UUID uuid) {
    return customersService
      .getCustomerById(uuid)
      .map(mapper::map)
      .orElseThrow(NotFoundException::new);
  }


}
