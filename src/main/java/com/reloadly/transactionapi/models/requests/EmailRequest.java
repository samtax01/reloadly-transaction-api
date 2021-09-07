package com.reloadly.transactionapi.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {

    @NotNull
    @Email
    @Column(length = 50)
    private String to;

    @NotNull
    @Column(length = 100)
    private String subject;

    @NotNull
    private String body;

}
