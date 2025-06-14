package com.cards.cards.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class TransferExceptions extends Exception {

    public ResponseEntity<Throwable> NotEnough() {
        return new ResponseEntity(new Exception("Not enough money!").getMessage(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Throwable> Ok() {
        return new ResponseEntity(new Exception("Transaction complete.").getMessage(), HttpStatus.OK);
    }
}
