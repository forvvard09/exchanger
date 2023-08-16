package org.example.exchanger.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ConversionError extends RuntimeException{
    public ConversionError(String message) {
        super(message);
    }
}
