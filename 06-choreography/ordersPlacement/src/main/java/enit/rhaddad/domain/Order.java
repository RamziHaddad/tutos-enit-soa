package enit.rhaddad.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;



@Entity
@Data
@Table(name = "Orders")
public class Order{
    @Id
    @GeneratedValue
    private UUID id;
    @OneToMany(mappedBy="order",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderItem> items = new ArrayList<>();
    private String customer;
    private LocalDateTime receivedAt = LocalDateTime.now(ZoneId.of("UTC"));
    private BigDecimal price = BigDecimal.ZERO;

    //pour JPA
    public Order() {
    }
    

    public Order(String customer) {
        this.customer = customer;
    }

    public void addOrderItem(OrderItem oi){
        oi.setOrder(this);
        items.add(oi);
    }
    public void addItems(List<OrderItem> ois){
        for(OrderItem oi:ois){
            addOrderItem(oi);
        }
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
        Order other = (Order) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }


}
