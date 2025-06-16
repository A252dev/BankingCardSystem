package com.cards.cards.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
public class UserExceptions extends Exception {

    public ResponseEntity<Throwable> NotFound() {
        return new ResponseEntity(new Exception("Not found!").getMessage(), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Throwable> Ok() {
        return new ResponseEntity(new Exception("User has updated.").getMessage(), HttpStatus.OK);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<Throwable> EmailIsExists() {
        return new ResponseEntity(new Exception("The email is already exists!").getMessage(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Throwable> PhoneIsExists() {
        return new ResponseEntity(new Exception("The phone is already exists!").getMessage(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Throwable> EmailOrPhoneIsExists() {
        return new ResponseEntity(new Exception("The email or phone is already exists!").getMessage(),
                HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Throwable> Created() {
        return new ResponseEntity(new Exception("User has created.").getMessage(), HttpStatus.OK);
    }

    public ResponseEntity<Throwable> Deleted() {
        return new ResponseEntity(new Exception("User has deleted.").getMessage(), HttpStatus.OK);
    }
}
