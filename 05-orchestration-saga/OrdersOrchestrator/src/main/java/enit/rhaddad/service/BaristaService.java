package enit.rhaddad.service;

import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import enit.rhaddad.api.dto.MakeOrderCommand;

@Path("/barista")
@RegisterRestClient(configKey="barista-api")
public interface BaristaService {
    @POST
    public void makeOrder(MakeOrderCommand cmd);

    @GET
    @Path("/{id}/status")
    public String getOrderStatus(@PathParam("id") UUID id);
}
