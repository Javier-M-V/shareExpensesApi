package com.jmv.expenses.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmv.expenses.dto.BalanceSheetDTO;
import com.jmv.expenses.dto.PaymentDTO;
import com.jmv.expenses.exception.GroupNotFoundException;
import com.jmv.expenses.exception.PersonNotFoundException;
import com.jmv.expenses.models.Group;
import com.jmv.expenses.models.Payment;
import com.jmv.expenses.models.Person;
import com.jmv.expenses.repository.GroupRepository;
import com.jmv.expenses.repository.PaymentRepository;
import com.jmv.expenses.repository.PersonRepository;
import com.jmv.expenses.services.api.IPaymentService;
import com.jmv.expenses.support.DtoToEntity;

@Service
public class PaymentService implements IPaymentService {

	@Autowired
	private PaymentRepository paymentRepo;

	@Autowired
	private GroupRepository groupRepo;

	@Autowired
	private PersonRepository personRepo;
	
	@Autowired
	private DtoToEntity dtoToEntity;

	@Override
	public List<Payment> getAllPaymentsByGroupId(Long id) {

		Iterable<Long> ids = this.findPaymentIdsFromGroup(id);

		return StreamSupport.stream(paymentRepo.findByIdInOrderByDateOfPaymentDesc(ids).spliterator(), false)
				.collect(Collectors.toList());
	}

	public List<Payment> getAllPayments() {

		return StreamSupport.stream(paymentRepo.findAll().spliterator(), false).collect(Collectors.toList());
	}

	public List<Payment> getAllPaymentsByUser(Long id) {

		Person person = this.findPersonById(id);
		return person.getListPayments();
	}

	public void save(PaymentDTO payment) {

		Person person = this.findPersonById(payment.getIdPerson());
		Payment pay = dtoToEntity.paymentDtoToPaymentEntity(payment, person);

		paymentRepo.save(pay);
	}

	public List<BalanceSheetDTO> getBalanceOfGroup(Long id) {

		List<BalanceSheetDTO> balance = new ArrayList<>();
		Group group = groupRepo.findById(id).orElseThrow(()-> new GroupNotFoundException(id));

		Double sum = group.getPersonsList().stream().flatMap(item -> item.getListPayments().stream())
				.mapToDouble(Payment::getAmount).sum();

		Double avg = sum / group.getPersonsList().size();

		group.getPersonsList().forEach(item -> {

			Double personSum = item.getListPayments().stream().mapToDouble(Payment::getAmount).sum();
			Double debt = personSum - avg;
			balance.add(new BalanceSheetDTO(item.getName(), item.getSurname(), Precision.round(debt, 2)));
		});

		return balance;
	}

	private List<Long> findPaymentIdsFromGroup(Long id) {

		Group group = groupRepo.findById(id).orElseThrow(()-> new GroupNotFoundException(id));
		List<Long> ids = group.getPersonsList().stream().flatMap(person -> person.getListPayments().stream())
					.map(Payment::getId).collect(Collectors.toList());

		return ids;
	}
	
	private Person findPersonById(Long id) {
		
		return personRepo.findById(id).orElseThrow(()-> new PersonNotFoundException(id));
	}
}
