package com.jmv.expenses.restcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jmv.expenses.models.Payment;
import com.jmv.expenses.services.IPaymentService;

@RequestMapping("/payment")
@RestController
public class PaymentRestController {
	
	@Autowired
	private IPaymentService paymentService;
	
	@GetMapping(value = "/all")
	public @ResponseBody List<Payment> getAllPayments(){
		
		return paymentService.getAllPayments();
	}
	
	@GetMapping(value = "/bygroup")
	public @ResponseBody List<Payment> getAllPaymentsByGroupId(@RequestParam long id){
		
		return paymentService.getAllPaymentsByGroupId(id);
	}
}
