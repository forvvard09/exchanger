package org.example.exchanger;

import org.example.config.MyConfig;
import org.example.exchanger.model.Currency;
import org.example.exchanger.service.AccountService;
import org.example.exchanger.service.Services;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;

public class AccountServiceTest {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
        Services services = context.getBean("accountService", AccountService.class);
        context.close();

        services.open("Иванов ОО", Currency.DOLLAR);
        services.open("Петров ИИ", Currency.EURO);
        services.open("Сидоров АА", Currency.RUBLE);

        //services.close(408178401);
        //services.close(408178401);
        /*
        System.out.println("Конвертация");
        System.out.println(services.conversion("рубль", new BigDecimal(60), "доллар"));
        System.out.println(services.conversion("рубль", new BigDecimal(70), "евро"));
        System.out.println(services.conversion("доллар", new BigDecimal(10), "рубль"));
        System.out.println(services.conversion("евро", new BigDecimal(10), "рубль"));
        System.out.println(services.conversion("доллар", new BigDecimal(10), "евро"));
        System.out.println(services.conversion("евро", new BigDecimal(10), "доллар"));
         */

        System.out.println(services.enoughSum("408179782", new BigDecimal(30), Currency.RUBLE));
        System.out.println(services.getInfo(408179782));
        System.out.println(services.getInfo(408178103));
        services.transfer("408179782", "408178103", new BigDecimal(5));
        System.out.println(services.getInfo(408179782));
        System.out.println(services.getInfo(408178103));
        services.debit("408178103", new BigDecimal(1), Currency.DOLLAR);
        System.out.println(services.getInfo(408178103));

    }
}
