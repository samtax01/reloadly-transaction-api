package com.reloadly.transactionapi.repositories;

import com.reloadly.transactionapi.configs.StaticData;
import com.reloadly.transactionapi.helpers.CustomException;
import com.reloadly.transactionapi.helpers.HttpRequest;
import com.reloadly.transactionapi.models.Transaction;
import com.reloadly.transactionapi.models.requests.EmailRequest;
import com.reloadly.transactionapi.models.requests.TransactionRequest;
import com.reloadly.transactionapi.models.responses.TransactionResponse;
import com.reloadly.transactionapi.repositories.interfaces.ITransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class TransactionRepository {

    @Value("${api.mail}")
    private String mailApiLink;

    private final ITransactionRepository iTransactionRepository;
    private final ModelMapper modelMap;


    public TransactionRepository(ITransactionRepository iTransactionRepository, ModelMapper modelMap) {
        this.iTransactionRepository = iTransactionRepository;
        this.modelMap = modelMap;
    }


    /**
     * Get single Transaction
     */
    public Mono<TransactionResponse> get(Long id) throws CustomException{
        var Transaction = iTransactionRepository.findById(id);
        if(Transaction.isEmpty())
            throw new CustomException(StaticData.transactionNotFound, HttpStatus.NOT_FOUND);
        return Mono.just(modelMap.map(Transaction.get(), TransactionResponse.class));
    }
    


    /**
     * Get all Transactions
     */
    public Mono<List<TransactionResponse>> gets(){
        return Mono.just(iTransactionRepository.findAll().stream().map(x-> modelMap.map(x, TransactionResponse.class) ).collect(Collectors.toList()));
    }

    /**
     * Create Transaction
     */
    public Mono<TransactionResponse> create(TransactionRequest request, String loggedInEmail) throws CustomException {
        if(!request.getCustomerEmail().equals(loggedInEmail))
            throw new CustomException(StaticData.invalidUser.replace("{email}", loggedInEmail), HttpStatus.UNAUTHORIZED);

        Transaction transaction = modelMap.map(request, Transaction.class);

        // Save Transaction
        iTransactionRepository.saveAndFlush(transaction);

        // Send Mail to Transaction in the background
        if(transaction.getId() > 0){
            sendNotificationEmail(transaction);
        }
        return Mono.just(modelMap.map(transaction, TransactionResponse.class));
    }


    /**
     * Update Transaction
     */
    public Mono<TransactionResponse> update(long id, TransactionRequest request) throws CustomException {
        var Transaction = iTransactionRepository.findById(id);
        if(Transaction.isEmpty())
            throw new CustomException(StaticData.transactionNotFound, HttpStatus.NOT_FOUND);
        var existingTransaction = Transaction.get();

        existingTransaction.setOrderId(request.getOrderId());
        existingTransaction.setStatus(request.getStatus());
        existingTransaction.setCustomerId(request.getCustomerId());
        existingTransaction.setCustomerEmail(request.getCustomerEmail());
        existingTransaction.setAmount( request.getAmount() );
        existingTransaction.setProductName(request.getProductName() );
        existingTransaction.setProductDescription( request.getProductDescription() );
        existingTransaction.setMetaData( request.getMetaData());
        existingTransaction.setUpdatedAt( LocalDateTime.now() );

        iTransactionRepository.saveAndFlush(existingTransaction);
        return Mono.just(modelMap.map(existingTransaction, TransactionResponse.class));
    }



    /**
     * Send Background Email Notification
     */
    @Async
    public void sendNotificationEmail(Transaction transaction){
        var emailRequest = EmailRequest.builder()
                .toEmail(transaction.getCustomerEmail())
                .subject(StaticData.transactionSuccessfulSubject)
                .body(StaticData.transactionMailBody)
                .build();
        try{
            HttpRequest.make(mailApiLink, HttpMethod.POST, emailRequest, String.class).subscribe( x->log.info("Email sent successfully. " + x) );
        }catch (Exception ex){
            log.error("Unable to send email. " + ex.getMessage());
        }
    }


}
