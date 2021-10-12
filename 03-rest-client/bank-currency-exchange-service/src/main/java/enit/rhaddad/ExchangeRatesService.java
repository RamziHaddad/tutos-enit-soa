package enit.rhaddad;

import java.math.BigDecimal;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "http://localhost:8081/exchange-rate")
public interface ExchangeRatesService {
    @POST
    @Path("/value")
    BigDecimal getExchangeRateValue(RateRequestDTO request);
    @POST
    @Path("/info")
    RateResponseDTO getExchangeRateInfo(RateRequestDTO request);
}
