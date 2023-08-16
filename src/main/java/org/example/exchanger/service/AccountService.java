package org.example.exchanger.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.exchanger.exception.ConversionError;
import org.example.exchanger.exception.DebitException;
import org.example.exchanger.exception.StorageException;
import org.example.exchanger.model.Account;
import org.example.exchanger.model.Currency;
import org.example.exchanger.repository.AccountStorage;
import org.example.exchanger.util.ExchangeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static java.math.BigDecimal.ROUND_DOWN;
import static org.example.exchanger.util.AccountUtil.generateNumber;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService implements Services {

    @Autowired
    private final AccountStorage storage;

    @Autowired
    private final ExchangeUtil exchangeUtil;


    @Override
    public void open(String name, Currency currency) {
        long id = generateNumber(currency.getCode());
        Account account = new Account(currency, name);
        account.setId(id);
        account.setSum(BigDecimal.valueOf(5));
        account.setOpen(true);
        try {
            storage.save(account);
        } catch (StorageException e) {
            log.error("An error occurred while creating an account! The account already exists.");
        }
    }

    @Override
    public void close(long id) {
        Account account = getAccount(id);
        account.setOpen(false);
        account.setSum(BigDecimal.valueOf(0));
        storage.delete(id);
    }

    @Override
    public Account getInfo(long id) {
        return getAccount(id);
    }

    private Account getAccount(long id) {
        Account account = storage.getById(id);
        if (account == null) {
            throw new StorageException("Account not found.");
        }
        return account;
    }

    @Override
    public BigDecimal conversion(String currencyFrom, BigDecimal sum, String currencyTo) {
        BigDecimal result = new BigDecimal(-1);
        if (currencyFrom.equals(Currency.RUBLE.getName())) {
            result = conversionRuble(sum, currencyTo);
        } else if (currencyFrom.equals(Currency.DOLLAR.getName())) {
            result = conversionDollar(sum, currencyTo);
        } else if (currencyFrom.equals(Currency.EURO.getName())) {
            result = conversionEuro(sum, currencyTo);
        }
        if (Integer.valueOf(result.intValue()) == -1) {
            throw new ConversionError("An error occurred while converting");
        }
        return result;
    }

    @Override
    public boolean enoughSum(String id, BigDecimal sum, Currency currency) {
        Account account = getAccount(Long.parseLong(id));
        if (!currency.equals(account.getCurrency())) {
            sum = conversionOfCurrencies(account, sum, currency);
        }
        return account.getSum().compareTo(sum) >= 0;
    }

    @Override
    public void debit(String id, BigDecimal sumDebit, Currency currency) {
        Account account = getAccount(Long.parseLong(id));
        if (enoughSum(id, sumDebit, currency)) {
            if (!currency.equals(account.getCurrency())) {
                sumDebit = conversionOfCurrencies(account, sumDebit, currency);
                account.setSum(account.getSum().subtract(sumDebit));
            } else {
                account.setSum(account.getSum().subtract(sumDebit));
            }

        } else {
            throw new DebitException("Error. Insufficient funds to transfer.");
        }


    }

    @Override
    public void inflow(String id, BigDecimal sumIn, Currency currency) {
        Account account = getAccount(Long.parseLong(id));
        if (!currency.equals(account.getCurrency())) {
            sumIn = conversionOfCurrencies(account, sumIn, currency);
            account.setSum(account.getSum().add(sumIn));
        } else {
            account.setSum(account.getSum().add(sumIn));
        }
    }

    @Override
    public void transfer(String idFrom, String idTo, BigDecimal sumTransfer) {
        Account from = getAccount(Long.parseLong(idFrom));
        Account to = getAccount(Long.parseLong(idTo));
        if (enoughSum(idFrom, sumTransfer, from.getCurrency())) {
            debit(idFrom, sumTransfer, from.getCurrency());
            inflow(idTo, sumTransfer, from.getCurrency());
            //процент
            sumTransfer = conversionOfCurrencies(to, sumTransfer, from.getCurrency());
            log.info("Рассчитываем сумму трансфера: " + sumTransfer);
            BigDecimal commission = sumTransfer.multiply(exchangeUtil.getCommission()).divide(new BigDecimal(100));
            log.info("Рассчитываем комиссию: " + commission);
            debit(idTo, commission, to.getCurrency());
        } else {
            throw new DebitException("Error. Insufficient funds to transfer.");
        }
    }


    private BigDecimal conversionOfCurrencies(Account account, BigDecimal sum, Currency currency) {
        BigDecimal result = new BigDecimal(-1);
        if (currency.getName().equals(Currency.RUBLE.getName())) {
            result = conversionRuble(sum, account.getCurrency().getName());
        } else if (currency.getName().equals(Currency.DOLLAR.getName())) {
            result = conversionDollar(sum, account.getCurrency().getName());
        } else if (currency.getName().equals(Currency.EURO.getName())) {
            result = conversionEuro(sum, account.getCurrency().getName());
        }
        return result;
    }

    private BigDecimal conversionRuble(BigDecimal sum, String currencyTo) {
        BigDecimal result = new BigDecimal(-1);
        if (currencyTo.equals(Currency.DOLLAR.getName())) {
            result = sum.divide(exchangeUtil.getDollarToRuble(), 6, ROUND_DOWN);
            return result;
        } else if (currencyTo.equals(Currency.EURO.getName())) {
            result = sum.divide(exchangeUtil.getEuroToRuble(), 6, ROUND_DOWN);
            return result;
        }
        return result;
    }

    private BigDecimal conversionDollar(BigDecimal sum, String currencyTo) {
        BigDecimal result = new BigDecimal(-1);
        if (currencyTo.equals(Currency.RUBLE.getName())) {
            result = sum.multiply(exchangeUtil.getDollarToRuble());
            return result;
        } else if (currencyTo.equals(Currency.EURO.getName())) {
            result = sum.divide(exchangeUtil.getEuroToDollar(), 6, ROUND_DOWN);
            return result;
        }
        return result;
    }

    private BigDecimal conversionEuro(BigDecimal sum, String currencyTo) {
        BigDecimal result = new BigDecimal(-1);
        if (currencyTo.equals(Currency.RUBLE.getName())) {
            result = sum.multiply(exchangeUtil.getEuroToRuble());
            return result;
        } else if (currencyTo.equals(Currency.DOLLAR.getName())) {
            result = sum.multiply(exchangeUtil.getEuroToDollar());
            return result;
        }
        return result;
    }
}
