package org.example.exchanger.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class StorageException extends RuntimeException {
    public StorageException(String message) {
        super(message);
    }
}
