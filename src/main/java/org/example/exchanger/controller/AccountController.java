package org.example.exchanger.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.exchanger.dto.AccountDto;
import org.example.exchanger.model.Account;
import org.example.exchanger.service.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = AccountController.GLOBAL_REST_URL)
@Slf4j
public class AccountController {

    @Autowired
    Services services;

    static final String GLOBAL_REST_URL = "/bank/api";

    @PostMapping(path = "/account/create")
    public String createAccount(@RequestBody AccountDto accountDto) {
        return String.valueOf(services.open(accountDto.getName(), accountDto.getCurrency()));
    }

    @PostMapping(path = "/account/close/{id}")
    public String closeAccount(@PathVariable long id) {
        services.close(id);
        return "Успешно";
    }

    @PostMapping(path = "/account/close")
    public String closeAccountJson(@RequestBody AccountDto accountDto) {
        long id = Long.parseLong(accountDto.getId());
        services.close(id);
        return "Успешно";
    }

    @GetMapping("/account")
    public Account getInfo(@RequestParam @NonNull String account) {
        long id = Long.parseLong(account);
        return services.getInfo(id);
    }

    //сигнатура задания не совпадет
    /*
    @GetMapping("/account/{id}")
    public Account getInfo(@PathVariable int id) {
        return services.getInfo(id);
    }
     */

    //сигнатура задания не совпадет
    /*
    @GetMapping(path = "/account")
    public Account getInfoJson(@RequestBody AccountDto accountDto) {
        log.info(String.valueOf(accountDto.getId()));
        log.info(accountDto.getName());
        long id = Long.parseLong(accountDto.getId());
        return services.getInfo(id);
    }
     */

    @PostMapping(path = "/account/payment")
    public String payment(@RequestBody AccountDto accountDto) {
        services.debit(accountDto.getId(), accountDto.getDebitSum(), accountDto.getCurrency());
        return "Успешно";

    }

    @PostMapping(path = "/account/income")
    public String income(@RequestBody AccountDto accountDto) {
        services.inflow(accountDto.getId(), accountDto.getInSum(), accountDto.getCurrency());
        return "Успешно";
    }

    @PostMapping(path = "/account/transfer")
    public String transfer(@RequestBody AccountDto accountDto) {
        services.transfer(accountDto.getId(), accountDto.getIdTo(), accountDto.getInSum());
        return "Успешно";
    }
}
