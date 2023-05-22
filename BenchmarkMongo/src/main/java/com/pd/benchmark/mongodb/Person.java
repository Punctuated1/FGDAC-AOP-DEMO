package com.pd.benchmark.mongodb;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.pd.benchmark.dataobjects.BenchmarkConstants;
import com.pd.benchmark.dataobjects.PersonRecord;
@Document(collection = "person")

public class Person {

	@Id
	private long id;

	public String lastName;

	public String firstName;

	public String gender;

	public LocalDate dateOfBirth;

	public LocalDateTime tsCr;

	public LocalDateTime tsup;

	public String nameUp;

	public String cag;

	/**
	* 
	*/
	public Person() {
		super();
// TODO Auto-generated constructor stub
	}

	public Person(PersonRecord personRecord) {
		this.id = personRecord.getPersonId();
		this.lastName = personRecord.getLastName();
		this.firstName = personRecord.getFirstName();
		this.gender = personRecord.getGender();
		this.dateOfBirth = personRecord.getDateOfBirth();
		this.tsCr = LocalDateTime.now();
		this.cag=personRecord.getCag();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public LocalDateTime getTsCr() {
		return tsCr;
	}

	public void setTsCr(LocalDateTime tsCr) {
		this.tsCr = tsCr;
	}

	public LocalDateTime getTsup() {
		return tsup;
	}

	public void setTsup(LocalDateTime tsup) {
		this.tsup = tsup;
	}

	public String getNameUp() {
		return nameUp;
	}

	public void setNameUp(String nameUp) {
		this.nameUp = nameUp;
	}

	public String getCag() {
		return cag;
	}

	public void setCag(String cag) {
		this.cag = cag;
	}	

}
