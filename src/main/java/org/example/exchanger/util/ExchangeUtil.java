package org.example.exchanger.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ExchangeUtil {

    @Value("${exchange.dollar.to.ruble}")
    public String dollarToRuble;

    @Value("${exchange.euro.to.ruble}")
    public String euroToRuble;

    @Value("${exchange.euro.to.dollar}")
    public String euroToDollar;

    @Value("${commission.percent}")
    public String commission;

    public BigDecimal getDollarToRuble() {
        return new BigDecimal(dollarToRuble);
    }

    public BigDecimal getEuroToRuble() {
        return new BigDecimal(euroToRuble);
    }

    public BigDecimal getEuroToDollar() {
        return new BigDecimal(euroToDollar);
    }

    public BigDecimal getCommission() {
        return new BigDecimal(commission);
    }
}
