package enit.rhaddad.api;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import enit.rhaddad.api.dto.CreateOrderCommand;
import enit.rhaddad.api.dto.OrderViewDTO;
import enit.rhaddad.domain.Order;
import enit.rhaddad.domain.OrderItem;
import enit.rhaddad.service.OrderService;

@Path("/orders")
public class OrdersResource {
    
    @Inject
    OrderService orderService;

    @POST
    @Transactional
    public OrderViewDTO placeNewOrder(CreateOrderCommand cmd){
        Order order = new Order(cmd.customer(),cmd.cardNumber(),cmd.cardCode());
        List<OrderItem> items = cmd.items().stream().map(oiDTO->new OrderItem(oiDTO.coffeeType(),oiDTO.quantity())).toList();
        order.addItems(items);
        orderService.receiveNewOrder(order);
        return new OrderViewDTO(order);
    }

    @GET
    public List<OrderViewDTO> allOrder(){
        List<Order> orders = orderService.getAllOrders();
        return orders.stream().map(OrderViewDTO::new).toList();
    }

    @GET
    @Path("/{id}")
    public Response orderById(@PathParam("id") UUID id){
        Optional<Order> o = orderService.getOrderById(id);
        if(o.isPresent()){
            return Response.status(Response.Status.OK).entity(new OrderViewDTO(o.get())).build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).entity(id).build();
        }
    }
}
