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

import org.eclipse.microprofile.config.inject.ConfigProperty;

import enit.rhaddad.domain.Payment;
import enit.rhaddad.service.DuplicatePaymentException;
import enit.rhaddad.service.PaymentService;

@Path("/payment")
public class PaymentResource {

    @ConfigProperty(name="app.bank.latency-ms",defaultValue = "0")// 0 temps de latence avant de traiter la demande en ms
    long latencyMs;
    @ConfigProperty(name="app.bank.failure-probability",defaultValue = "0")//entre 0 et 100
    int failureProbability;

    @Inject
    PaymentService paymentService;

    @POST
    @Transactional
    public Response makeNewPayment(MakePaymentCommand cmd){
        if(latencyMs>0){
            try {
                Thread.sleep(latencyMs);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        double random = Math.random();
        if(random*100<failureProbability){//le service de paiement se plante et ne peut pas faire le paiement
            throw new RuntimeException();
        } 
        if(cmd.cardNumber()==cmd.cardCode()){
            Payment p = new Payment(cmd.transactionId(), cmd.amount());
            try {
                paymentService.makeNewPayment(p);
                return Response.status(Response.Status.OK).build();
            } catch (DuplicatePaymentException e) {
                //already paid, returning ok
                return Response.status(Response.Status.OK).build();
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
