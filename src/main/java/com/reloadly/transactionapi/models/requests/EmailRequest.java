package com.reloadly.transactionapi.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {

    @NotNull
    @Email
    private String toEmail;

    @NotNull
    @Email
    private String fromEmail;

    @NotNull
    private String subject;

    @NotNull
    private String body;
}
