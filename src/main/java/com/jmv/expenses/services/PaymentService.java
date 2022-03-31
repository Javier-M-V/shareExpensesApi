package com.jmv.expenses.services;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmv.expenses.dao.GroupRepository;
import com.jmv.expenses.dao.PaymentRepository;
import com.jmv.expenses.models.Group;
import com.jmv.expenses.models.Payment;

@Service
public class PaymentService implements IPaymentService {
	
	@Autowired
	private PaymentRepository paymentRepo;
	
	@Autowired
	private GroupRepository groupRepo;

	@Override
	public List<Payment> getAllPaymentsByGroupId(Long id) {
		
		List<Long> ids = findPaymentIdsFromGroup(id);
		
		return StreamSupport.stream(paymentRepo.findAllById(ids).spliterator(), false)
				.collect(Collectors.toList());
	}
	
	public List<Payment> getAllPayments() {
		
		return StreamSupport.stream(paymentRepo.findAll().spliterator(), false)
			    .collect(Collectors.toList());
	}
	
	private List<Long> findPaymentIdsFromGroup(Long id) {
		
		Group group = groupRepo.findById(id).get();
		
		List <Long> ids = group.getPersonsList()
				.stream().flatMap(person-> person.getListPayments().stream())
				.map(Payment::getId)
				.collect(Collectors.toList());
		
		return ids;
	}
}
