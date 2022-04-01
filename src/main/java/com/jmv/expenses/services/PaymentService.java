package com.jmv.expenses.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmv.expenses.dao.GroupRepository;
import com.jmv.expenses.dao.PaymentRepository;
import com.jmv.expenses.dao.PersonRepository;
import com.jmv.expenses.models.Group;
import com.jmv.expenses.models.Payment;
import com.jmv.expenses.models.Person;

@Service
public class PaymentService implements IPaymentService {
	
	@Autowired
	private PaymentRepository paymentRepo;
	
	@Autowired
	private GroupRepository groupRepo;
	
	@Autowired
	private PersonRepository personRepo;

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
	
	public List<Payment> getAllPaymentsByUser(Long id) {
		
		Optional<Person> opPerson = personRepo.findById(id);
		
		if (opPerson.isPresent()) {
			
			return opPerson.get().getListPayments();
		}
		
		else return new ArrayList<Payment>();
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
