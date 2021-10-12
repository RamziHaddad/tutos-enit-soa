package enit.rhaddad;

import java.math.BigDecimal;
import java.util.Currency;

public class ExchangeRateResponseDTO {
    
    private Currency from;
    private Currency to;    
    private BigDecimal rate;
    private String text;
    public ExchangeRateResponseDTO() {
    }


 
    public ExchangeRateResponseDTO(Currency from, Currency to, BigDecimal rate) {
        this.from = from;
        this.to = to;
        this.rate = rate;
        text = String.format("1 %s = %s %s", from.getSymbol(), rate, to.getSymbol());
    }



    public Currency getFrom() {
        return from;
    }



    public void setFrom(Currency from) {
        this.from = from;
    }



    public Currency getTo() {
        return to;
    }



    public void setTo(Currency to) {
        this.to = to;
    }



    public BigDecimal getRate() {
        return rate;
    }



    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }



    public String getText() {
        return text;
    }



    public void setText(String text) {
        this.text = text;
    }



}
