package com.jmv.expenses.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.JoinColumn;

@Entity
@Table(name="persons")
public class Person {
	
	@Id
	@Column(name = "person_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "surname", nullable = false)
	private String surname;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "groups_persons", 
	joinColumns = @JoinColumn(name = "person_id", nullable=false), 
	inverseJoinColumns = @JoinColumn(name = "group_id", nullable=false))
	private List<Group> groupList;
	
	@JsonIgnore
	@OneToMany(mappedBy="PersonPaid" , cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Payment> listPayments;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public List<Group> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<Group> groupList) {
		this.groupList = groupList;
	}

	public List<Payment> getListPayments() {
		return listPayments;
	}

	public void setListPayments(List<Payment> listPayments) {
		this.listPayments = listPayments;
	}
}
