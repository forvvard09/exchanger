package org.example.exchanger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Currency {
    @JsonProperty("доллар")
    DOLLAR(840, "доллар"),
    @JsonProperty("рубль")
    RUBLE(810, "рубль"),
    @JsonProperty("евро")
    EURO(978, "евро");

    private final int code;
    private final String name;
}
