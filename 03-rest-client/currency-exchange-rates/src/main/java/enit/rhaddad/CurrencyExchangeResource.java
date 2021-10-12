package enit.rhaddad;

import java.math.BigDecimal;
import java.util.Currency;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class CurrencyExchangeResource {
    @Inject
    CurrencyExchangeRatesRepository currencyExchangeRatesService;

    @POST
    @Path("/exchange-rate/info")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ExchangeRateResponseDTO findExchangeRateInfo(ExchangeRateRequestDTO request) throws UnknownCurrencyException {
        Currency from = Currency.getInstance(request.getFrom());
        Currency to = Currency.getInstance(request.getTo());
        BigDecimal rate = currencyExchangeRatesService.getExchangeRate(from, to);
        return new ExchangeRateResponseDTO(from,to,rate);
    }
    @POST
    @Path("/exchange-rate/value")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public BigDecimal findExchangeRateValue(ExchangeRateRequestDTO request) throws UnknownCurrencyException {
        Currency from = Currency.getInstance(request.getFrom());
        Currency to = Currency.getInstance(request.getTo());
        BigDecimal rate = currencyExchangeRatesService.getExchangeRate(from, to);
        return rate;
    }
    @GET
    @Path("/currency-description/{currencyCode}")
    public Currency findExchangeRate(@PathParam("currencyCode") String currencyCode) throws UnknownCurrencyException {
        return Currency.getInstance(currencyCode);
    }
}
