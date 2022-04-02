package com.javier.expenses.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jmv.expenses.exception.GroupNotFoundException;
import com.jmv.expenses.models.Group;
import com.jmv.expenses.models.Payment;
import com.jmv.expenses.models.Person;
import com.jmv.expenses.repository.GroupRepository;
import com.jmv.expenses.repository.PaymentRepository;
import com.jmv.expenses.repository.PersonRepository;
import com.jmv.expenses.services.impl.PaymentService;

import junit.framework.TestCase;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest extends TestCase{
	
	@InjectMocks
	private PaymentService paymentService;
	
	@Mock
	private PaymentRepository paymentRepo;
	
	@Mock
	private GroupRepository groupRepo;
	
	@Mock
	private PersonRepository personRepo;

	static Optional<Person> opPerson;
	
	static Optional<Person> opPersonNull = Optional.ofNullable(null);
	
	static Optional<Group> opGroupNull = Optional.ofNullable(null);
	
	static Person person;
	
	static Group group;
	
	static Optional<Group> opGroup;
	
	static List<Person> listPersons;
	
	static List<Payment> listPayments;
	
	static Payment payment;
	
	@BeforeAll
	static void setup() {
		
		group = new Group();
		person = new Person();
		person.setId(33L);
		person.setGroupList(new ArrayList<Group>());
		
		payment = new Payment();
		payment.setAmount(22.0);
		payment.setId(2L);
		payment.setDescription("chips");
		payment.setPersonPaid(person);
		
		listPayments = new ArrayList<>();
		listPayments.add(payment);
		
		person.setListPayments(listPayments);
		group.setPersonsList(Arrays.asList(person));
		opPerson = Optional.of(person);
		opGroup = Optional.of(group);
		
		listPersons = new ArrayList<>();
		listPersons.add(person);
		
		
	}
	
	@Test
	void test_getAllPaymentsByGroupId_get_payments() {
		
		Mockito.when(groupRepo.findById(Mockito.anyLong())).thenReturn(opGroup);
		Mockito.when(paymentRepo.findByIdInOrderByDateOfPaymentDesc(Mockito.anyCollection())).thenReturn(listPayments);
		
		try {
			List<Payment> listPayments = paymentService.getAllPaymentsByGroupId(2L);
			
			verify(paymentRepo, times(1)).findByIdInOrderByDateOfPaymentDesc(Arrays.asList(2L));
			assertSame(payment, listPayments.get(0));
			
		}catch (GroupNotFoundException e) {
			fail();
		}
	}
	
	@Test
	void test_getAllPaymentsByGroupId_throws_GroupNotFoundException() {
		
		Mockito.when(groupRepo.findById(Mockito.anyLong())).thenReturn(opGroupNull);
		
		try {
			paymentService.getAllPaymentsByGroupId(2L);
			fail();
		}catch (GroupNotFoundException e) {
			verify(groupRepo, times(1)).findById(2L);
		}
	}
}
