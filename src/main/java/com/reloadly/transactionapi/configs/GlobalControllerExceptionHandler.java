package com.reloadly.transactionapi.configs;

import com.reloadly.transactionapi.helpers.CustomException;
import com.reloadly.transactionapi.helpers.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.SQLException;

/**
 * Handle global exception
 */
@Slf4j
@ResponseBody
@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    /**
     * ConversionFailedException
     */
    @ExceptionHandler({ConversionFailedException.class, HttpClientErrorException.NotAcceptable.class, SQLException.class})
    public ResponseEntity<Response<String>> handleConversion(Exception ex) {
        return new ResponseEntity<>(Response.errorMessage(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * UNAUTHORIZED
     */
    @ExceptionHandler({BadCredentialsException.class, AuthenticationException.class})
    public ResponseEntity<Response<String>> handleAuthException(Exception ex) {
        return new ResponseEntity<>(Response.errorMessage(ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    /**
     * CustomException
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Response<String>> handleCustomException(CustomException ex) {
        return new ResponseEntity<>(Response.errorMessage(ex.getMessage()), ex.getStatus());
    }


    /**
     * General
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<String>> handleOtherException(Exception ex) {

        return new ResponseEntity<>(Response.errorMessage(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
