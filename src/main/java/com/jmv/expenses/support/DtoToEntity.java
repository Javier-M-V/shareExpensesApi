package com.jmv.expenses.support;

import org.springframework.stereotype.Service;

import com.jmv.expenses.dto.PaymentDTO;
import com.jmv.expenses.models.Payment;
import com.jmv.expenses.models.Person;

@Service
public class DtoToEntity {	
	
	public Payment paymentDtoToPaymentEntity(PaymentDTO payment, Person person) {

		Payment pay = new Payment();
		pay.setAmount(payment.getAmount());
		pay.setDateOfPayment(payment.getDateOfPayment());
		pay.setDescription(payment.getDescription());
		pay.setPersonPaid(person);
		return pay;
	}
}
