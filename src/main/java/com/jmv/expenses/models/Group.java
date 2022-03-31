package com.jmv.expenses.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="groups")
public class Group {
	
	@Id
	@Column(name = "group_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="name_group", nullable = true)
	private String nameGroup;
	
	@JsonIgnore
	@ManyToMany (mappedBy = "groupList")
	private List<Person> personsList;

	public long getId() {
		return id;
	}

	public List<Person> getPersonsList() {
		return personsList;
	}

	public void setPersonsList(List<Person> personsList) {
		this.personsList = personsList;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNameGroup() {
		return nameGroup;
	}

	public void setNameGroup(String nameGroup) {
		this.nameGroup = nameGroup;
	}
}
