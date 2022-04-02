package com.jmv.expenses.support;

import java.util.Optional;

import com.jmv.expenses.dto.PaymentDTO;
import com.jmv.expenses.models.Payment;
import com.jmv.expenses.models.Person;

public class DtoToEntity {
	
	private DtoToEntity() {}	
	
	public static Payment paymentDtoToPaymentEntity(PaymentDTO payment, Optional<Person> opPerson) {

		Payment pay = new Payment();
		pay.setAmount(payment.getAmount());
		pay.setDateOfPayment(payment.getDateOfPayment());
		pay.setDescription(payment.getDescription());
		pay.setPersonPaid(opPerson.get());
		return pay;
	}
}
