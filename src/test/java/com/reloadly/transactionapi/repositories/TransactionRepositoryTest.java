package com.reloadly.transactionapi.repositories;

import com.reloadly.transactionapi.Seeder;
import com.reloadly.transactionapi.configs.AuthenticationManager;
import com.reloadly.transactionapi.helpers.AuthorisationHelper;
import com.reloadly.transactionapi.helpers.CustomException;
import com.reloadly.transactionapi.repositories.interfaces.ITransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.test.StepVerifier;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionRepositoryTest {


    @Autowired
    private TransactionRepository transactionRepository;

    @MockBean
    private ITransactionRepository iTransactionRepository;


    @Test
    void canCreateTransaction() throws CustomException {
        // Arrange
        var data = Seeder.getTransactionRequest();
        AuthenticationManager.currentUser = new AuthorisationHelper.Payload(data.getCustomerEmail(), null, true, Collections.emptyList());

        // Act
        var Transaction = transactionRepository.create(data);
        StepVerifier
                .create(Transaction)
                .expectNextMatches(response ->
                        // Assert
                        !Objects.isNull(response))
                .verifyComplete();
    }


    @Test
    void canUpdateTransaction() throws CustomException {
        // Arrange
        var existingTransaction = Seeder.getTransaction();
        existingTransaction.setId(1);
        Mockito.doReturn(Optional.of(existingTransaction)).when(iTransactionRepository).findById(Mockito.any());

        // Act
        var Transaction = transactionRepository.update(1L, Seeder.getTransactionRequest());
        StepVerifier
                .create(Transaction)
                .expectNextMatches(response ->
                        // Assert
                        response.getId() == existingTransaction.getId() )
                .verifyComplete();
    }


    @Test
    void getTransaction() throws CustomException {
        // Arrange
        var existingTransaction = Seeder.getTransaction();
        existingTransaction.setId(1);
        Mockito.doReturn(Optional.of(existingTransaction)).when(iTransactionRepository).findById(Mockito.any());

        // Act
        var Transaction = transactionRepository.get(1L);
        StepVerifier
                .create(Transaction)
                .expectNextMatches(response ->
                        // Assert
                        response.getId() == existingTransaction.getId() )
                .verifyComplete();
    }
}
