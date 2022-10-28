package enit.rhaddad.api;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import enit.rhaddad.api.dto.OrderViewDTO;
import enit.rhaddad.domain.Order;
import enit.rhaddad.service.BaristaService;

@Path("/barista")
public class BaristaResource {
    
    @Inject
    BaristaService barista;


    @GET
    public List<OrderViewDTO> allOrder(){
        List<Order> orders = barista.getAllOrders();
        return orders.stream().map(OrderViewDTO::new).toList();
    }

    @GET
    @Path("/{id}")
    public Response orderById(@PathParam("id") UUID id){
        Optional<Order> o = barista.getOrderById(id);
        if(o.isPresent()){
            return Response.status(Response.Status.OK).entity(new OrderViewDTO(o.get())).build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).entity(id).build();
        }
    }

    @GET
    @Path("/{id}/status")
    public Response orderStatusById(@PathParam("id") UUID id){
        Optional<Order> o = barista.getOrderById(id);
        if(o.isPresent()){
            return Response.status(Response.Status.OK).entity(o.get().getStatus().toString()).build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).entity(id).build();
        }
    }
}
