package org.example.exchanger.runer;

import org.example.exchanger.model.Currency;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println(Currency.EURO.getCode());
        System.out.println(Currency.EURO.getName());

        Map<Integer, String> map = new HashMap<>();

        map.put(1, "Один");
        map.put(2, "Два");
        map.put(3, "Три");
        String str1 =  map.putIfAbsent(1, "Четыре");

        map.forEach((key, value) -> System.out.println(key + " " + value));
        System.out.println(str1);

        //map.forEach((key, value) -> System.out.println(key + " " + value));



    }
}
