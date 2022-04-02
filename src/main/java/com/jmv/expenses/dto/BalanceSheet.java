package com.jmv.expenses.dto;

public class BalanceSheet {
	
	private String name;
	
	private String surname;
	
	private Double balance;
	
	public BalanceSheet(String name, String surname, Double balance) {
		super();
		this.name = name;
		this.surname = surname;
		this.balance = balance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
	
}
