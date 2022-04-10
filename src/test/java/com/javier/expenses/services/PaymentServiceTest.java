package com.javier.expenses.services;

import static org.mockito.Mockito.mockitoSession;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

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
import com.jmv.expenses.services.impl.PaymentService;
import com.jmv.expenses.support.DtoToEntity;

import junit.framework.TestCase;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest extends TestCase {

	@InjectMocks
	private PaymentService paymentService;

	@Mock
	private PaymentRepository paymentRepo;

	@Mock
	private GroupRepository groupRepo;

	@Mock
	private PersonRepository personRepo;
	
	@Mock
	private DtoToEntity dtoToEntity;

	private static final Date NOW = new Date();
	
	static Optional<Person> opPerson;

	static Optional<Person> opPersonNull = Optional.ofNullable(null);

	static Optional<Group> opGroupNull = Optional.ofNullable(null);

	static Person person;

	static Group group;

	static Optional<Group> opGroup;

	static List<Person> listPersons;

	static List<Payment> listPayments;

	static Payment payment;
	
	static PaymentDTO paymentDto;

	@BeforeEach
	void setup() {

		group = new Group();
		person = new Person();
		person.setId(33L);
		person.setName("Joe");
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
		
		paymentDto = new PaymentDTO();
		paymentDto.setIdPerson(5L);
		paymentDto.setAmount(22.0);
		paymentDto.setDescription("Dinner");
		paymentDto.setDateOfPayment(NOW);
	}

	@Test
	void test_getAllPaymentsByGroupId_get_payments() {

		Mockito.when(groupRepo.findById(Mockito.anyLong())).thenReturn(opGroup);
		Mockito.when(paymentRepo.findByIdInOrderByDateOfPaymentDesc(Mockito.anyCollection())).thenReturn(listPayments);

		try {
			List<Payment> listPayments = paymentService.getAllPaymentsByGroupId(2L);

			verify(paymentRepo, times(1)).findByIdInOrderByDateOfPaymentDesc(Arrays.asList(2L));
			assertSame(payment, listPayments.get(0));

		} catch (GroupNotFoundException e) {
			fail();
		}
	}

	@Test
	void test_getAllPaymentsByGroupId_throws_GroupNotFoundException() {

		Mockito.when(groupRepo.findById(Mockito.anyLong())).thenReturn(opGroupNull);

		try {
			paymentService.getAllPaymentsByGroupId(2L);
			fail();

		} catch (GroupNotFoundException e) {
			verify(groupRepo, times(1)).findById(2L);
		}
	}

	@Test
	void test_getAllPayments_return_one_calls_findAll() {

		Mockito.when(paymentRepo.findAll()).thenReturn(listPayments);

		try {
			List<Payment> listPayments = paymentService.getAllPayments();

			verify(paymentRepo, times(1)).findAll();
			assertSame(payment, listPayments.get(0));

		} catch (GroupNotFoundException e) {
			fail();
		}
	}

	@Test
	void test_getAllPaymentsByUser_return_all_payments() {

		Mockito.when(personRepo.findById(2L)).thenReturn(opPerson);

		try {
			List<Payment> listPayments = paymentService.getAllPaymentsByUser(2L);

			verify(personRepo, times(1)).findById(2L);
			assertSame(payment, listPayments.get(0));

		} catch (GroupNotFoundException e) {
			fail();
		}
	}

	@Test
	void test_getAllPaymentsByUser_throws_PersonNotFoundException() {

		Mockito.when(personRepo.findById(2L)).thenReturn(opPersonNull);

		try {
			paymentService.getAllPaymentsByUser(2L);
			fail();
		} catch (PersonNotFoundException e) {
			verify(personRepo, times(1)).findById(2L);
		}
	}
	
	@Test
	void save_calls_repo_save() {
		
		Mockito.when(personRepo.findById(5L)).thenReturn(opPerson);		
		Mockito.when(dtoToEntity.paymentDtoToPaymentEntity(Mockito.any(PaymentDTO.class), Mockito.any(Person.class))).thenReturn(new Payment());
		
		try {
			paymentService.save(paymentDto);
			
			verify(personRepo, times(1)).findById(5L);
			verify(paymentRepo, times(1)).save(Mockito.any(Payment.class));
		} catch (PersonNotFoundException e) {
			fail();
		}
	}
	
	@Test
	void save_throws_PersonNotFoundException () {
		
		Mockito.when(personRepo.findById(5L)).thenReturn(opPersonNull);
		
		try {
			paymentService.save(paymentDto);
			fail();
		} catch (PersonNotFoundException e) {
			verify(personRepo, times(1)).findById(5L);
			verify(paymentRepo, times(0)).save(Mockito.any(Payment.class));
		}
	}
	
	@Test
	void test_getBalanceOfGroup_get_exact_balance() {
		
		Person person2 = new Person();
		person2.setId(33L);
		person2.setName("Jim");
		person2.setGroupList(new ArrayList<Group>());
		payment = new Payment();
		payment.setAmount(5.0);
		payment.setId(1L);
		payment.setDescription("market");
		payment.setPersonPaid(person2);

		listPayments = new ArrayList<>();
		listPayments.add(payment);

		person2.setListPayments(listPayments);
		
		group.setPersonsList(Arrays.asList(person2, person));
		
		Optional<Group> opGroup2 = Optional.of(group);
		
		Mockito.when(groupRepo.findById(Mockito.anyLong())).thenReturn(opGroup2);
		try {
			
			List<BalanceSheetDTO> balance = paymentService.getBalanceOfGroup(2L);
			
			assertEquals(2,balance.size());
			assertEquals(-8.5, balance.get(0).getBalance());
			assertEquals(8.5, balance.get(1).getBalance());
			
		} catch (GroupNotFoundException e) {
			fail();
		}
	}
	
	@Test
	void test_getBalanceOfGroup_throws_GroupNotFoundException() {
		
		Mockito.when(groupRepo.findById(Mockito.anyLong())).thenReturn(opGroupNull);
		try {
			
			paymentService.getBalanceOfGroup(2L);
			fail();
		} catch (GroupNotFoundException e) {
			verify(groupRepo, times(1)).findById(2L);
		}
	}
}
