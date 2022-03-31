package com.jmv.expenses.dao;

import org.springframework.data.repository.CrudRepository;

import com.jmv.expenses.models.Group;

public interface GroupRepository extends CrudRepository<Group, Long> {

}
