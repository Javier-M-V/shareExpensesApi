package com.jmv.expenses.services;

import java.util.List;

import com.jmv.expenses.dto.BalanceSheetDTO;
import com.jmv.expenses.models.Payment;

public interface IPaymentService {
	
	public List<Payment> getAllPaymentsByGroupId (Long id);
	
	public List<Payment> getAllPayments();
	
	public List<Payment> getAllPaymentsByUser(Long id);
	
	public List<BalanceSheetDTO> getBalanceOfGroup(Long id);
	
	public void save(Payment payment);
}
