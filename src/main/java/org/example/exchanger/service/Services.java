package org.example.exchanger.service;

import org.example.exchanger.model.Account;
import org.example.exchanger.model.Currency;

import java.math.BigDecimal;

public interface Services {
    long open(String name, Currency currency);

    void close(long id);

    Account getInfo(long id);

    BigDecimal conversion(String currencyFrom, BigDecimal sum, String currencyTo);

    boolean enoughSum(String id, BigDecimal sum, Currency currency);

    void debit(String id, BigDecimal sumDebit, Currency currency);

    void inflow(String id, BigDecimal sumIn, Currency currency);

    void transfer(String idFrom, String idTo, BigDecimal sumIn);
}
