package com.cards.cards.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cards.cards.dao.CardDTO;
import com.cards.cards.dao.TransferDTO;
import com.cards.cards.models.AccountModel;
import com.cards.cards.services.CardService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "User card manager")
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping("/user-add-card")
    public ResponseEntity<Throwable> addCard(@RequestBody CardDTO cardDTO) {
        return cardService.addUserCard(cardDTO);
    }

    @PutMapping("/user-edit-card")
    public ResponseEntity<Throwable> editCard(@RequestBody CardDTO cardDTO) {
        return cardService.editUserCard(cardDTO);
    }

    @GetMapping("/user-get-cards")
    public ResponseEntity<Optional<List<AccountModel>>> getAllCards() {
        return new ResponseEntity(cardService.getAllCards(), HttpStatus.OK);
    }

    @PutMapping(path = "/user-card-transfer")
    public ResponseEntity<Throwable> transfer(@RequestBody TransferDTO transferDTO) {
        return cardService.transfer(transferDTO);
    }
}
