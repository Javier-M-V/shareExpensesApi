package com.jmv.expenses.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

		Optional<Person> opPerson = personRepo.findById(id);

		if (opPerson.isPresent()) {

			return opPerson.get().getListPayments();

		} else {
			throw new PersonNotFoundException(id);
		}
	}

	public void save(PaymentDTO payment) {

		Optional<Person> opPerson = personRepo.findById(payment.getIdPerson());

		if (opPerson.isPresent()) {

			Payment pay = DtoToEntity.paymentDtoToPaymentEntity(payment, opPerson);

			paymentRepo.save(pay);

		} else {
			throw new PersonNotFoundException(payment.getIdPerson());
		}
	}

	public List<BalanceSheetDTO> getBalanceOfGroup(Long id) {

		List<BalanceSheetDTO> balance = new ArrayList<>();
		Optional<Group> group = groupRepo.findById(id);

		if (group.isPresent()) {

			Double sum = group.get().getPersonsList().stream().flatMap(item -> item.getListPayments().stream())
					.mapToDouble(Payment::getAmount).sum();

			Double avg = sum / group.get().getPersonsList().size();

			group.get().getPersonsList().forEach(item -> {

				Double personSum = item.getListPayments().stream().mapToDouble(Payment::getAmount).sum();
				Double debt = avg - personSum;
				balance.add(new BalanceSheetDTO(item.getName(), item.getSurname(), Precision.round(debt, 2)));
			});

			return balance;
		} else {
			throw new GroupNotFoundException(id);
		}
	}

	private List<Long> findPaymentIdsFromGroup(Long id) {

		Optional<Group> group = groupRepo.findById(id);

		if (group.isPresent()) {

			List<Long> ids = group.get().getPersonsList().stream().flatMap(person -> person.getListPayments().stream())
					.map(Payment::getId).collect(Collectors.toList());

			return ids;

		} else {
			throw new GroupNotFoundException(id);
		}
	}
}
