package enit.rhaddad;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CurrencyExchangeRatesRepository {
    private Map<Currency,Map<Currency,BigDecimal>> rates = new HashMap<>();

    {
        Currency usd = Currency.getInstance("USD");
        Currency tnd = Currency.getInstance("TND");
        Currency eur = Currency.getInstance("EUR");
        rates.put(usd,Map.of(tnd,BigDecimal.valueOf(2.8),eur,BigDecimal.valueOf(0.9)));
        rates.put(tnd,Map.of(usd,BigDecimal.valueOf(0.35),eur,BigDecimal.valueOf(0.3)));
        rates.put(eur,Map.of(tnd,BigDecimal.valueOf(3.1),usd,BigDecimal.valueOf(1.1)));
    }

    public BigDecimal getExchangeRate(Currency from, Currency to) throws UnknownCurrencyException{
        if(rates.containsKey(from) && rates.containsKey(to)){
            return rates.get(from).get(to);            
        }
        throw new UnknownCurrencyException("unknown currency");
    }
}
