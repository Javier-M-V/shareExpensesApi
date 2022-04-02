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
import com.jmv.expenses.dto.BalanceSheet;
import com.jmv.expenses.models.Group;
import com.jmv.expenses.models.Payment;
import com.jmv.expenses.models.Person;
import com.jmv.expenses.support.ApiConstants;

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
		
		Iterable<Long> ids = findPaymentIdsFromGroup(id);
		
		return StreamSupport.stream(paymentRepo.findByIdInOrderByDateOfPaymentDesc(ids).spliterator(), false)
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
	
	public List<BalanceSheet> getBalanceOfGroup(Long id) {

		List<BalanceSheet> balance = new ArrayList<>();

		Group group = groupRepo.findById(id).get();

		Double sum = group.getPersonsList().stream().flatMap(item -> item.getListPayments().stream())
				.mapToDouble(Payment::getAmount).sum();

		Double avg = sum / group.getPersonsList().size();

		group.getPersonsList().forEach(item -> {

			Double personSum = item.getListPayments().stream().mapToDouble(Payment::getAmount).sum();
			Double debt = avg - personSum;
			balance.add(new BalanceSheet(item.getName(), item.getSurname(), debt));
		});

		return balance;
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
