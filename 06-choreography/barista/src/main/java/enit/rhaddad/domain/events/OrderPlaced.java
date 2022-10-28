package enit.rhaddad.domain.events;

import java.math.BigDecimal;
import java.util.Map;

import enit.rhaddad.domain.CoffeeType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
/**
 * OrderPlaced
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class OrderPlaced extends BaseEvent{
    Map<CoffeeType,Long> items;
    BigDecimal price;
    String customer;

}