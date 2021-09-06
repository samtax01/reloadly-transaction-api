package com.reloadly.transactionapi.repositories.interfaces;

import com.reloadly.transactionapi.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransactionRepository extends JpaRepository<Transaction, Long> {

}
