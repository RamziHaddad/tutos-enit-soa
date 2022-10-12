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

import enit.rhaddad.domain.Payment;
import enit.rhaddad.service.DuplicatePaymentException;
import enit.rhaddad.service.PaymentService;

@Path("/payment")
public class PaymentResource {
    
    @Inject
    PaymentService paymentService;

    @POST
    @Transactional
    public Response makeNewPayment(MakePaymentCommand cmd){
        double latencyProbability = Math.random();
        if(latencyProbability>0.5d){//50% des appels seront lents
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        double successProbability = Math.random();
        if(successProbability>0.5d){//50% de chance que le service de paiement se plante et ne peut pas faire le paiement
            throw new RuntimeException();
        } 
        if(cmd.cardNumber()==cmd.cardCode()){
            Payment p = new Payment(cmd.transactionId(), cmd.amount());
            try {
                paymentService.makeNewPayment(p);
                return Response.status(Response.Status.OK).build();
            } catch (DuplicatePaymentException e) {
                return Response.status(Response.Status.CONFLICT).build();
            }
        }
        return Response.status(Response.Status.EXPECTATION_FAILED).build();
    }

    @GET
    public List<Payment> allPayment(){
        return paymentService.getAllPayments();
    }

    @GET
    @Path("/{id}")
    public Response paymentById(@PathParam("id") UUID id){
        Optional<Payment> o = paymentService.getPaymentById(id);
        if(o.isPresent()){
            return Response.status(Response.Status.OK).entity(o.get()).build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).entity(id).build();
        }
    }
}
