package com.horiaconstantin.springboot.webappseed.repository;

import com.horiaconstantin.springboot.webappseed.domain.Transaction;
import com.horiaconstantin.springboot.webappseed.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
	List<Transaction> findByUserOrderByTransactionDateDesc(User username);

	Optional<Transaction> findFirstByUserOrderByTransactionDateDesc(User username);
}