package com.reloadly.transactionapi.controllers;

import com.reloadly.transactionapi.helpers.CustomException;
import com.reloadly.transactionapi.helpers.Response;
import com.reloadly.transactionapi.helpers.Validator;
import com.reloadly.transactionapi.models.requests.TransactionRequest;
import com.reloadly.transactionapi.models.responses.TransactionResponse;
import com.reloadly.transactionapi.repositories.TransactionRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionRepository repository;

    public TransactionController(TransactionRepository repository) {
        this.repository = repository;
    }

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Add a new transaction")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transaction Created", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request",  content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) })
    })
    public Mono<ResponseEntity<Response<TransactionResponse>>> createTransaction(@RequestBody TransactionRequest request, @CurrentSecurityContext(expression="authentication?.name") String loggedInEmail) throws CustomException {
        Validator.validate(request);
        return repository.create(request, loggedInEmail).map(x-> ResponseEntity.ok(Response.success(x)));
    }


    @Operation(summary = "Get a transaction information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the item", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }),
            @ApiResponse(responseCode = "404", description = "Item not found",  content = @Content)
    })
    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Response<TransactionResponse>>> getTransaction(@PathVariable final long id) throws CustomException {
        return repository.get(id).map(x-> ResponseEntity.ok(Response.success(x)));
    }


    @Operation(summary = "Get all transactions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item list", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }),
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Response<List<TransactionResponse>>>> getTransactions(){
        return repository.gets().map(x-> ResponseEntity.ok(Response.success(x)));
    }

}
