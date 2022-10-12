package enit.rhaddad.domain;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "OrdersItems")
@Data
public class OrderItem {
    @Id
    @GeneratedValue
    private UUID id;
    @ManyToOne
    private Order order;
    private CoffeeType coffeeType;
    private int quantity;
    private int quantityReady = 0;

    public OrderItem() {
    }

    public OrderItem(CoffeeType coffeeType, int quantity) {
        this.coffeeType = coffeeType;
        this.quantity = quantity;
    }

    public void incrementReady() {
        quantityReady++;
    }

    public boolean isReady() {
        return getRemainingQuantity()==0;
    }
    public int getRemainingQuantity(){
        return quantity-quantityReady;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OrderItem other = (OrderItem) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
