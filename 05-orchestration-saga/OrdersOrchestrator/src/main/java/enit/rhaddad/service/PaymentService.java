package enit.rhaddad.service;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import enit.rhaddad.api.dto.MakePaymentCommand;

@Path("/payment")
@RegisterRestClient(configKey="payment-api")
public interface PaymentService {
    @POST
    public void makePayment(MakePaymentCommand cmd);
}
