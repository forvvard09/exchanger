package org.example.exchanger.service;

import org.example.exchanger.models.Currency;
import org.example.exchanger.storages.Storage;

public interface Services {
    public void openAccount(String fio, Currency currency);

    public void closeAccount(String numberAccount);


}
