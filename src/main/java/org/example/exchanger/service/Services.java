package org.example.exchanger.service;

import org.example.exchanger.model.Currency;

public interface Services {
    void open(String name, Currency currency);

    void close(long id);

}
