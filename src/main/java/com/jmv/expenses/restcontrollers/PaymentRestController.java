package com.jmv.expenses.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jmv.expenses.dto.BalanceSheetDTO;
import com.jmv.expenses.models.Payment;
import com.jmv.expenses.services.api.IPaymentService;

@RequestMapping("/payment")
@RestController
public class PaymentRestController {
	
	@Autowired
	private IPaymentService paymentService;
	
	@GetMapping(value = "")
	public CollectionModel<Payment> getAllPayments(){
		
		return CollectionModel.of(paymentService.getAllPayments());
	}
	
	@GetMapping(value = "/user")
	public CollectionModel<Payment>  getAllPaymentsByPerson(@RequestParam long id){
		
		return CollectionModel.of(paymentService.getAllPaymentsByUser(id));
	}
	
	@GetMapping(value = "/group")
	public CollectionModel<Payment> getAllPaymentsByGroupId(@RequestParam long id){
		
		return CollectionModel.of(paymentService.getAllPaymentsByGroupId(id));
	}
	
	@GetMapping(value = "/balance")
	public CollectionModel<BalanceSheetDTO> getBalanceOfGroup(@RequestParam long id){
		
		return CollectionModel.of(paymentService.getBalanceOfGroup(id));
	}
	
	@PostMapping(value = "/post")
	public void post(@RequestParam Payment payment){
		
		paymentService.save(payment);
	}
}
