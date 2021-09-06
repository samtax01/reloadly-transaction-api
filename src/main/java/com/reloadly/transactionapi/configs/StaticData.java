package com.reloadly.transactionapi.configs;

import antlr.TokenStreamRewriteEngine;

public class StaticData {

    public static final String transactionSuccessfulSubject = "Your transaction is successful";
    public static final String transactionMailBody = "You have successfully made a transaction on Reloadly. Below is your transaction information";

    public static String transactionNotFound = "Transaction not found";

    public static String invalidUser = "Invalid Authorisation. Token not match with user credential. Expecting email {email}";
}
