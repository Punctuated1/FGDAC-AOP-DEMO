package com.pd.benchmark.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<PersonPg, Integer> {

	List<PersonPg> findByLastName(String lastName);

}
