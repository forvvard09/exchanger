package org.example.exchanger.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.exchanger.exception.StorageException;
import org.example.exchanger.model.Account;
import org.example.exchanger.model.Currency;
import org.example.exchanger.repository.AccountStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static org.example.exchanger.util.AccountUtil.generateNumber;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService implements Services {

    @Autowired
    private final AccountStorage storage;

    @Override
    public void open(String name, Currency currency) {
        long id = generateNumber(currency.getCode());
        Account account = new Account(currency, name);
        account.setId(id);
        account.setSum(BigDecimal.valueOf(1));
        account.setOpen(true);
        try {
            storage.save(account);
        } catch (StorageException e) {
            log.error("An error occurred while creating an account! The account already exists.");
        }
    }

    @Override
    public void close(long id) {
        Account account = storage.getById(id);
        if (account != null) {
            account.setOpen(false);
            account.setSum(BigDecimal.valueOf(0));
        }
        try {
            storage.delete(id);
        } catch (StorageException e) {
            log.error("An error occurred while closing the account! Account not found.");
        }
    }
}
