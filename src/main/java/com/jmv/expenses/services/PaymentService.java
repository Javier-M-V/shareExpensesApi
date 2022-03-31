package com.jmv.expenses.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmv.expenses.dao.GroupDao;
import com.jmv.expenses.dao.PaymentDao;
import com.jmv.expenses.models.Group;
import com.jmv.expenses.models.Payment;

@Service
public class PaymentService implements IPaymentService {
	
	@Autowired
	private PaymentDao paymentDao;
	
	@Autowired
	private GroupDao groupDao;

	@Override
	public List<Payment> getAllPaymentsByGroupId(Long id) {
		
		List<Long> ids = findPaymentIdsFromGroup(id);
		
		return StreamSupport.stream(paymentDao.findAllById(ids).spliterator(), false)
				.collect(Collectors.toList());
	}
	
	public List<Payment> getAllPayments() {
		
		return StreamSupport.stream(paymentDao.findAll().spliterator(), false)
			    .collect(Collectors.toList());
	}
	
	private List<Long> findPaymentIdsFromGroup(Long id) {
		
		Group group = groupDao.findById(id).get();
		
		List <Long> ids = group.getPersonsList()
				.stream().flatMap(person-> person.getListPayments().stream())
				.map(Payment::getId).
				collect(Collectors.toList());
		
		return ids;
	}
}
