package com.reloadly.transactionapi.models;

import com.reloadly.transactionapi.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String orderId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @NotNull
    private String customerId;

    @NotNull
    @Email
    private String customerEmail;

    private BigDecimal amount;

    @NotNull
    private String productName;

    @NotNull
    private String productDescription;

    private String metaData;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

}
