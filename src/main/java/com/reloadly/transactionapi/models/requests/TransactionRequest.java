package com.reloadly.transactionapi.models.requests;

import com.reloadly.transactionapi.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {

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

    @Min(value=1, message = "cannot be less than 1")
    private BigDecimal amount;

    @NotNull
    private String productName;

    @NotNull
    private String productDescription;

    private String metaData;

}
