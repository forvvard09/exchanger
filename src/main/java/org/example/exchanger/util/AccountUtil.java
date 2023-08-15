package org.example.exchanger.util;

import static org.example.exchanger.repository.Storage.COUNTER_ACCOUNT;

public class AccountUtil {
    private static final String PART_ACCOUNT = "40817";

    public static long generateNumber(int codeCurrency) {
        String str = String.format("%s%s%s", PART_ACCOUNT, codeCurrency, COUNTER_ACCOUNT);
        return Long.parseLong(str);
    }
}
