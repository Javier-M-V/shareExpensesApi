package com.jmv.expenses.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.jmv.expenses.models.Payment;

public interface PaymentRepository extends CrudRepository<Payment, Long>{
	
	public List<Payment> findByIdInOrderByDateOfPaymentDesc(Iterable<Long> ids);
}
