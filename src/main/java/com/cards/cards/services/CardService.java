package com.cards.cards.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cards.cards.dao.CardDTO;
import com.cards.cards.exceptions.CardExceptions;
import com.cards.cards.models.AccountModel;
import com.cards.cards.repositories.AccountRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CardService {

    @Autowired
    private UserService userService;

    private final AccountRepository _accountRepository;

    @Autowired
    private CardExceptions cardExceptions;

    public ResponseEntity<Throwable> addUserCard(Optional<CardDTO> cardDTO) {

        if (!cardDTO.isPresent())
            return cardExceptions.Error();
        _accountRepository.save(new AccountModel(cardDTO.get().getNumber(), userService.getAuthUserId().get(),
                cardDTO.get().getExpire(), cardDTO.get().getStatus(), cardDTO.get().getBalance()));
        return cardExceptions.Ok();
    }

    public ResponseEntity<Throwable> editUserCard(Optional<CardDTO> cardDTO) {

        if (!cardDTO.isPresent())
            return cardExceptions.Error();
        _accountRepository.save(new AccountModel(_accountRepository.findByNumber(cardDTO.get().getNumber()).getId(),
                cardDTO.get().getNumber(), userService.getAuthUserId().get(), cardDTO.get().getExpire(),
                cardDTO.get().getStatus(), cardDTO.get().getBalance()));
        return cardExceptions.InfoOk();
    }

    public Optional<List<AccountModel>> getAllCards() {
        Optional<List<AccountModel>> allCards = _accountRepository.findAllByUser_id(userService.getAuthUserId().get());
        if (allCards.isPresent())
            return allCards;
        return null;
    }
}
