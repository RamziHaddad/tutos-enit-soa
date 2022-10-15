package enit.rhaddad.service;

import java.math.BigDecimal;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import enit.rhaddad.domain.CoffeeType;

@ApplicationScoped
public class PricingService {
    private static Map<CoffeeType,BigDecimal> prices;
    static{
        prices = Map.of(CoffeeType.CAPPUCCINO, BigDecimal.valueOf(3),
                        CoffeeType.DOUBLE_ESPRESSO, BigDecimal.valueOf(2),
                        CoffeeType.ESPRESSO, BigDecimal.valueOf(1.2),
                        CoffeeType.LATTE, BigDecimal.valueOf(2.5)
                        );
    }

    public BigDecimal getCoffeePrice(CoffeeType type){
        return prices.getOrDefault(type, BigDecimal.ZERO);
    }
}
