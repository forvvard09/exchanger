package org.example.exchanger.dto;

import lombok.Data;
import org.example.exchanger.model.Currency;

import java.math.BigDecimal;

@Data
public class AccountDto {
    private String id;
    private String idTo;
    private String name;
    private Currency currency;
    private BigDecimal debitSum;
    private BigDecimal inSum;

}
