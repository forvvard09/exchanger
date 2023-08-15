package org.example.exchanger.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Currency {
    DOLLAR(840, "доллар"),
    RUBLE(810, "рубль"),
    EURO(978, "евро");

    private final int code;
    private final String name;
}
