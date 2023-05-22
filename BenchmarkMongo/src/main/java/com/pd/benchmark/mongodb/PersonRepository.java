package com.pd.benchmark.mongodb;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepository extends MongoRepository<Person, Long> {
	
	List<Person> findByLastName(String lastName);

}
