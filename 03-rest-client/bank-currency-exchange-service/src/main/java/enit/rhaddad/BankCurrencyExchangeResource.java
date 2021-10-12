package enit.rhaddad;

import java.math.BigDecimal;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/")
public class BankCurrencyExchangeResource {

    @RestClient
    ExchangeRatesService remoteExchangeService;
    @POST
    @Path("/conversion-request")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String convert(ConversionRequestDTO request) {

        RateRequestDTO remoteRequest = new RateRequestDTO(request.getFrom(), request.getTo());
        BigDecimal rate = remoteExchangeService.getExchangeRateValue(remoteRequest);
        BigDecimal result = rate.multiply(request.getAmount());
        String text = String.format("%s %s = %s %s", request.getAmount(),request.getFrom(),result,request.getTo());       
        return text;
    }
}