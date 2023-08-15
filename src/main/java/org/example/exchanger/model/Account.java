package org.example.exchanger.model;

import lombok.*;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Setter
@Getter
public class Account {
    private long id;
    @Setter(AccessLevel.NONE)
    @NonNull
    private Currency currency;
    @Setter(AccessLevel.NONE)
    @NonNull
    private String name;
    private BigDecimal sum;
    private boolean isOpen;

    @Override
    public String toString() {
        String str = isOpen ? " открыт " : " закрыт";
        return "Расчетный счет пользователя: " +
                "name='" + name + '\'' +
                ", сумма/остаток=" + sum +
                ", валюта=" + currency +
                str +
                '}';
    }
}
