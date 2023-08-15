package org.example.exchanger;

import org.example.config.MyConfig;
import org.example.exchanger.model.Currency;
import org.example.exchanger.service.AccountService;
import org.example.exchanger.service.Services;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AccountServiceTest {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
        Services services = context.getBean("accountService", AccountService.class);
        context.close();

        services.open("Иванов ОО", Currency.DOLLAR);
        services.open("Петров ИИ", Currency.EURO);
        services.open("Сидоров АА", Currency.RUBLE);

        services.close(408178401);
        services.close(408178401);
    }
}
