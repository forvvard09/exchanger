package org.example.exchanger.repository;

import org.example.exchanger.model.Account;

public interface AccountStorage {

    void save(Account account);

    void delete(long id);

    Account getById(long id);

}
