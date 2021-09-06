package com.reloadly.transactionapi.controllers;

import com.reloadly.transactionapi.Seeder;
import com.reloadly.transactionapi.helpers.AuthorisationHelper;
import com.reloadly.transactionapi.helpers.Response;
import com.reloadly.transactionapi.repositories.interfaces.ITransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Optional;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionControllerTest {


    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private AuthorisationHelper authorisationHelper;

    @MockBean
    private ITransactionRepository iTransactionRepository;

    @BeforeEach
    public void setUp() {
        // webTestClient = webTestClient.mutate().responseTimeout(Duration.ofMillis(30000)).build();
    }



    @Test
    void transactionNotFound(){
        // Arrange
        // Act
        webTestClient
                .get()
                .uri("/api/v1/transactions/1")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .is4xxClientError()
                .expectBody(Response.class)
                .value(response -> {
                    log.info("\nResponse is {}", response);

                    // Assert
                    Assertions.assertFalse(response.isStatus());
                });
    }

    @Test
    void getTransaction(){
        // Arrange
        var transaction = Seeder.getTransaction();
        transaction.setId(1);
        Mockito.doReturn(Optional.of(transaction)).when(iTransactionRepository).findById(Mockito.any());

        // Act
        webTestClient
                .get()
                .uri("/api/v1/transactions/1")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Response.class)
                .value(response -> {
                    log.info("\nResponse is {}", response);

                    // Assert
                    Assertions.assertTrue(response.isStatus());
                });
    }


}
