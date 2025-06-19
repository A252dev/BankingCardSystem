package com.cards.cards.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.cards.cards.dao.TransferDTO;
import com.cards.cards.exceptions.TransferExceptions;
import com.cards.cards.exceptions.UserExceptions;
import com.cards.cards.models.AccountModel;
import com.cards.cards.models.UserModel;
import com.cards.cards.repositories.AccountRepository;

public class CardServiceTests {

    @Mock
    private AccountRepository _accountRepository;

    @Mock
    private UserService userService;

    @Mock
    private TransferExceptions transferExceptions;

    @Mock
    private UserExceptions userExceptions;

    @InjectMocks
    private CardService cardService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testTransfer_Success() {
        TransferDTO transferDTO = new TransferDTO("0000 0000 0000 0001", "0000 0000 0000 0002", BigDecimal.valueOf(50));
        AccountModel senderAccount = new AccountModel("0000 0000 0000 0001", BigDecimal.valueOf(100),
                new UserModel(1, "John", Date.valueOf("1900-01-01"), "password"));
        AccountModel receiverAccount = new AccountModel("0000 0000 0000 0002", BigDecimal.valueOf(30),
                new UserModel(2, "Andy", Date.valueOf("1900-01-01"), "password"));
        when(_accountRepository.findByNumber("0000 0000 0000 0001")).thenReturn(Optional.of(senderAccount));
        when(_accountRepository.findByNumber("0000 0000 0000 0002")).thenReturn(Optional.of(receiverAccount));
        when(userService.getAuthUserId())
                .thenReturn(Optional.of(new UserModel(1, "John", Date.valueOf("1900-01-01"), "password")));

        ResponseEntity<Throwable> response = cardService.transfer(transferDTO);

        assertEquals(transferExceptions.Ok(), response);
        assertEquals(BigDecimal.valueOf(50), senderAccount.getBalance());
        assertEquals(BigDecimal.valueOf(80), receiverAccount.getBalance());
        verify(_accountRepository).save(receiverAccount);
    }

    @Test
    public void testTransfer_NotEnoughBalance() {
        TransferDTO transferDTO = new TransferDTO("0000 0000 0000 0001", "0000 0000 0000 0002",
                BigDecimal.valueOf(150));
        AccountModel senderAccount = new AccountModel("0000 0000 0000 0001", BigDecimal.valueOf(100),
                new UserModel(1, "John", Date.valueOf("1900-01-01"), "password"));
        AccountModel receiverAccount = new AccountModel("0000 0000 0000 0002", BigDecimal.valueOf(30),
                new UserModel(2, "Andy", Date.valueOf("1900-01-01"), "password"));
        when(_accountRepository.findByNumber("0000 0000 0000 0001")).thenReturn(Optional.of(senderAccount));
        when(_accountRepository.findByNumber("0000 0000 0000 0002")).thenReturn(Optional.of(receiverAccount));
        when(userService.getAuthUserId())
                .thenReturn(Optional.of(new UserModel(1, "John", Date.valueOf("1900-01-01"), "password")));
        when(transferExceptions.NotEnough()).thenReturn(ResponseEntity.badRequest().build());

        ResponseEntity<Throwable> response = cardService.transfer(transferDTO);

        assertEquals(transferExceptions.NotEnough(), response);
    }

    @Test
    public void testTransfer_AccountNotFound() {
        TransferDTO transferDTO = new TransferDTO("0000 0000 0000 0001", "0000 0000 0000 0002", BigDecimal.valueOf(50));
        when(_accountRepository.findByNumber("0000 0000 0000 0001")).thenReturn(Optional.empty());

        ResponseEntity<Throwable> response = cardService.transfer(transferDTO);

        assertEquals(userExceptions.NotFound(), response);
    }

    @Test
    public void testTransfer_UserNotAuthorized() {
        TransferDTO transferDTO = new TransferDTO("0000 0000 0000 0001", "0000 0000 0000 0002", BigDecimal.valueOf(50));
        AccountModel senderAccount = new AccountModel("0000 0000 0000 0001", BigDecimal.valueOf(100),
                new UserModel(2, "Andy", Date.valueOf("1900-01-01"), "password"));
        when(_accountRepository.findByNumber("0000 0000 0000 0001")).thenReturn(Optional.of(senderAccount));
        when(userService.getAuthUserId())
                .thenReturn(Optional.of(new UserModel(1, "John", Date.valueOf("1900-01-01"), "password")));

        ResponseEntity<Throwable> response = cardService.transfer(transferDTO);

        assertEquals(userExceptions.NotFound(), response);
    }

    @Test
    public void testTransfer_ReceiverAccountNotFound() {
        TransferDTO transferDTO = new TransferDTO("0000 0000 0000 0001", "0000 0000 0000 0002", BigDecimal.valueOf(50));
        AccountModel senderAccount = new AccountModel("0000 0000 0000 0001", BigDecimal.valueOf(100),
                new UserModel(1, "John", Date.valueOf("1900-01-01"), "password"));
        when(_accountRepository.findByNumber("0000 0000 0000 0001")).thenReturn(Optional.of(senderAccount));
        when(userService.getAuthUserId())
                .thenReturn(Optional.of(new UserModel(1, "John", Date.valueOf("1900-01-01"), "password")));
        when(_accountRepository.findByNumber("0000 0000 0000 0002")).thenReturn(Optional.empty());

        ResponseEntity<Throwable> response = cardService.transfer(transferDTO);

        assertEquals(userExceptions.NotFound(), response);
    }
}
