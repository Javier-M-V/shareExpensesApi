package com.jmv.expenses.dao;

import org.springframework.data.repository.CrudRepository;

import com.jmv.expenses.models.Payment;

public interface PaymentDao extends CrudRepository<Payment, Long>{

}
