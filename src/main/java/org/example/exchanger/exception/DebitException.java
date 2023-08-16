package org.example.exchanger.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DebitException extends RuntimeException {
    public DebitException(String message) {
        super(message);
    }
}
