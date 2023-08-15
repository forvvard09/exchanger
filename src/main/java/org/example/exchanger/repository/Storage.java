package org.example.exchanger.repository;

import org.example.exchanger.exception.StorageException;
import org.example.exchanger.model.Account;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class Storage implements AccountStorage {

    public static final AtomicInteger COUNTER_ACCOUNT = new AtomicInteger(1);

    private final Map<Long, Account> storage = new ConcurrentHashMap<>();

    @Override
    public void save(Account account) {
        if (storage.putIfAbsent(account.getId(), account) != null) {
            throw new StorageException();
        } else {
            COUNTER_ACCOUNT.incrementAndGet();
        }
    }

    @Override
    public void delete(long id) {
        if (storage.remove(id) == null) {
            throw new StorageException();
        } else {
            COUNTER_ACCOUNT.decrementAndGet();
        }
    }

    @Override
    public Account getById(long id) {
        return storage.get(id);
    }
}
