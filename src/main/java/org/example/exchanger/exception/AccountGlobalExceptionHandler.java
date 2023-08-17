package org.example.exchanger.exception;

import org.example.exchanger.exception.exception_handling.AccountIncorrectData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AccountGlobalExceptionHandler {

    /*
    @ExceptionHandler
    public ResponseEntity<AccountIncorrectData> handlerException(
        StorageException exception) {
        AccountIncorrectData data = new AccountIncorrectData();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

     */

    @ExceptionHandler
    public ResponseEntity<AccountIncorrectData> handlerException(
            RuntimeException exception) {
        AccountIncorrectData data = new AccountIncorrectData();
        data.setInfo(String.format("%s: %s", "В банке произошла ошибка", exception.getMessage()));
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }
}
