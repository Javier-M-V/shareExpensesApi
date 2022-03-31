package com.jmv.expenses.dao;

import org.springframework.data.repository.CrudRepository;

import com.jmv.expenses.models.Group;

public interface GroupDao extends CrudRepository<Group, Long> {

}
