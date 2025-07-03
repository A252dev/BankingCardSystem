package com.cards.cards.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cards.cards.dao.CardDTO;
import com.cards.cards.dao.TransferDTO;
import com.cards.cards.exceptions.CardExceptions;
import com.cards.cards.exceptions.TransferExceptions;
import com.cards.cards.exceptions.UserExceptions;
import com.cards.cards.models.AccountModel;
import com.cards.cards.repositories.AccountRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CardService {

    private Optional<AccountModel> findMyCardNumber = Optional.empty();

    @Autowired
    private UserService userService;

    private final AccountRepository _accountRepository;

    @Autowired
    private UserExceptions userExceptions;

    @Autowired
    private CardExceptions cardExceptions;

    @Autowired
    private TransferExceptions transferExceptions;

    public ResponseEntity<Throwable> addUserCard(CardDTO cardDTO) {

        if (_accountRepository.findByNumber(cardDTO.getNumber()).isPresent())
            return cardExceptions.Error();
        _accountRepository.save(new AccountModel(cardDTO.getNumber(), userService.getAuthUserId().get(),
                cardDTO.getExpire(), cardDTO.getStatus(), cardDTO.getBalance()));
        return cardExceptions.Ok();
    }

    public ResponseEntity<Throwable> editUserCard(CardDTO cardDTO) {
        if (_accountRepository.findByNumber(cardDTO.getNumber()).isPresent())
            return cardExceptions.Error();
        _accountRepository
                .save(new AccountModel(_accountRepository.findByNumber(cardDTO.getNumber()).get().getId(),
                        cardDTO.getNumber(), userService.getAuthUserId().get(), cardDTO.getExpire(),
                        cardDTO.getStatus(), cardDTO.getBalance()));
        return cardExceptions.InfoOk();
    }

    public Optional<List<AccountModel>> getAllCards() {
        Optional<List<AccountModel>> allCards = _accountRepository.findAllByUser_id(userService.getAuthUserId().get());
        if (allCards.isPresent())
            return allCards;
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public ResponseEntity<Throwable> transfer(TransferDTO transferDTO) {

        findMyCardNumber = _accountRepository.findByNumber(transferDTO.getNumber_from());

        if (findMyCardNumber.isPresent()
                && userService.getAuthUserId().get().getId().equals(findMyCardNumber.get().getUser_id().getId())) {

            Optional<AccountModel> findUserAccountId = _accountRepository.findByNumber(transferDTO.getNumber_to());
            if (findUserAccountId.isPresent()) {
                if (findMyCardNumber.get().getBalance().compareTo(transferDTO.getAmount()) <= 0) {
                    return transferExceptions.NotEnough();
                } else {
                    System.out.println("My balance before: " + findMyCardNumber.get().getBalance());
                    System.out.println("Target user balance before: " + findUserAccountId.get().getBalance());
                    findMyCardNumber.get()
                            .setBalance(
                                    findMyCardNumber.get().getBalance().subtract(transferDTO.getAmount()));
                    findUserAccountId.get()
                            .setBalance(
                                    findUserAccountId.get().getBalance()
                                            .add(transferDTO.getAmount()));
                    _accountRepository.save(findUserAccountId.get());
                    System.out.println("My balance after: " + findMyCardNumber.get().getBalance());
                    System.out.println("Target user balance after: " + findUserAccountId.get().getBalance());
                }
            } else {
                return userExceptions.NotFound();
            }
        } else {
            return userExceptions.NotFound();
        }
        return transferExceptions.Ok();
    }
}
