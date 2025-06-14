package com.cards.cards.services;

import java.util.Optional;

import javax.smartcardio.CardException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cards.cards.dao.CardDTO;
import com.cards.cards.exceptions.CardExceptions;
import com.cards.cards.repositories.AccountRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CardService {

    private final AccountRepository _accountRepository;

    @Autowired
    private CardExceptions cardExceptions;

    public ResponseEntity<Throwable> addUserCard(Optional<CardDTO> cardDTO) {

        if (!cardDTO.isPresent())
            return cardExceptions.Error();
        return cardExceptions.Ok();
    }

}
