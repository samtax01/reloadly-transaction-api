package com.reloadly.transactionapi;

import com.reloadly.transactionapi.enums.TransactionStatus;
import com.reloadly.transactionapi.models.Transaction;
import com.reloadly.transactionapi.models.requests.EmailRequest;
import com.reloadly.transactionapi.models.requests.TransactionRequest;
import com.reloadly.transactionapi.models.responses.TransactionResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.math.BigDecimal;

public class Seeder {


    public static TransactionRequest getTransactionRequest(){
        return TransactionRequest
                .builder()
                .orderId("1")
                .status(TransactionStatus.COMPLETED)
                .customerId("1")
                .customerEmail("hello@samsonoyetola.com")
                .amount(BigDecimal.valueOf(300))
                .productName("Vodafone Airtime")
                .productDescription("Airtime")
                .metaData(null)
                .build();
    }

    public static Transaction getTransaction(){
        var model = new ModelMapper();
        model.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return model.map(getTransactionRequest(), Transaction.class);
    }

    public static TransactionResponse getCustomerResponse(){
        var model = new ModelMapper();
        model.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return model.map(getCustomerResponse(), TransactionResponse.class);
    }

    public static EmailRequest getEmailRequest(){
        return EmailRequest
                .builder()
                .to("hello@samsonoyetola.com")
                .subject("Hi")
                .body("Hello World")
                .build();
    }

}
