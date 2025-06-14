package com.cards.cards.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardExceptions extends Exception {

    public ResponseEntity<Throwable> Error() {
        return new ResponseEntity(new Exception("Error!").getMessage(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Throwable> Ok() {
        return new ResponseEntity(new Exception("Card has added.").getMessage(), HttpStatus.OK);
    }
}
